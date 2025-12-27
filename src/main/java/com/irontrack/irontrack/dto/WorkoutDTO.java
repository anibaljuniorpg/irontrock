package com.irontrack.irontrack.dto;

import com.irontrack.irontrack.entity.Workout;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutDTO(Long id, String title, String note, String type, String category, String duration, List<ExerciseDTO> exercises, LocalDateTime scheduledDateTime, boolean isCompleted) {
    public WorkoutDTO(Workout workout) {
        this(
                workout.getId(),
                workout.getTitle(),
                workout.getNote(),
                workout.getType(),
                workout.getCategory(),
                workout.getDuration_minutes(),
                workout.getExercises()
                        .stream()
                        .map(ExerciseDTO::new)
                        .toList(),
                workout.getScheduledDataTime(),
                workout.isCompleted()
        );
    }
}
