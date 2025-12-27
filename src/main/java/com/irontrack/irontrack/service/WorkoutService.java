package com.irontrack.irontrack.service;

import com.irontrack.irontrack.dto.ExerciseDTO;
import com.irontrack.irontrack.dto.WorkoutDTO;
import com.irontrack.irontrack.entity.Exercise;
import com.irontrack.irontrack.entity.User;
import com.irontrack.irontrack.entity.Workout;
import com.irontrack.irontrack.repository.UserRepository;
import com.irontrack.irontrack.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenJwtService jwtService;

    public WorkoutDTO createWorkout(WorkoutDTO workoutDTO){
        User user = getCurrentUser();
        Workout workout = new Workout(workoutDTO);
        workout.setUser(user);
        return new WorkoutDTO(workoutRepository.save(workout));
    }

    public WorkoutDTO updateWorkout(Long id, WorkoutDTO workoutDTO){
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        updateWorkoutFromDTO(workout, workoutDTO);
        return new WorkoutDTO(workoutRepository.save(workout));
    }

    public void deleteWorkout(Long id){workoutRepository.deleteById(id);}

    public List<WorkoutDTO> listWorkouts(){
        User user = getCurrentUser();
        return workoutRepository.findByUserOrderByScheduledDataTimeAsc(user).stream()
                .map(workout -> new WorkoutDTO(workout))
                .collect(Collectors.toList());
    }

    public String generateReport(){
        User user = getCurrentUser();
        List<Workout> workouts = workoutRepository.findByUserAndIsCompletedTrue(user);

        StringBuilder report = new StringBuilder();
        report.append("Relatorio de treino para ").append(user.getUsername());

        if (workouts.isEmpty()){
            report.append("\n\nNenhum treino concluido encontrado\n\n");
        } else {
            report.append("Total de treinos concluidos: ").append(workouts.size());
            for (Workout workout : workouts){
                report.append("\n\nTreino: ").append(workout.getTitle());
                report.append("\n\nData: ").append(workout.getScheduledDataTime());
                report.append("\n\nTipo: ").append(workout.getType());
                report.append("\n\nDuração: ").append(workout.getDuration_minutes());
                report.append("\n\nExercicios:\n");

                for (Exercise exercise : workout.getExercises()){
                    report.append("  - ").append(exercise.getName())
                            .append(": ").append(exercise.getSets())
                            .append(" series, ").append(exercise.getReps())
                            .append(" repetições, ").append(exercise.getWight())
                            .append(" kg\n");

                }
                report.append("\n");
            }
            return report.toString();
        }

        return null;
    }

    /*
    * principal → crachá
    UserDetails → informações do crachá
    User (entidade) → pessoa real no sistema
    * */
    private User getCurrentUser() {
        //Obtém a autenticação atual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Valida se existe usuário autenticado
        if (authentication == null || !authentication.isAuthenticated()){
            throw new RuntimeException("No authenticated user found");
        }

        /**Obtém o principal.
        *@param principal prepresenta o usuário autenticado (normalmente um UserDetails)
         */
        Object principal = authentication.getPrincipal();
        /*Extrai o username. Pega o username usado no login.*/
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        throw new RuntimeException("Unexpected authentication principal type");
    }



    private void updateWorkoutFromDTO(Workout workout, WorkoutDTO dto){
        workout.setTitle(dto.title());
        workout.setNote(dto.note());
        workout.setType(dto.type());
        workout.setCategory(dto.category());
        workout.setDuration_minutes(dto.duration());
        workout.setScheduledDataTime(dto.scheduledDateTime());
        workout.setCompleted(dto.isCompleted());

        if (dto.exercises() != null) {
            workout.getExercises().clear();

            List<Exercise> exercises = dto.exercises().stream()
                    .map(exerciseDTO -> new Exercise(exerciseDTO))
                    .collect(Collectors.toList());
            workout.getExercises().addAll(exercises);

            exercises.forEach(exercise -> exercise.setWorkout(workout));
        }


    }

    private Exercise mapExerciseDtoToEntity(ExerciseDTO exerciseDTO) {
        Exercise exercise = new Exercise();
        if (exerciseDTO.id() != null) {
            exercise.setId(exerciseDTO.id());
        }
        exercise.setName(exerciseDTO.name());
        exercise.setSets(exerciseDTO.sets());
        exercise.setReps(exerciseDTO.reps());
        exercise.setWight(exerciseDTO.weight());
        return exercise;
    }



}
