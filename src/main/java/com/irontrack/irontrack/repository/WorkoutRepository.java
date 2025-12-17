package com.irontrack.irontrack.repository;

import com.irontrack.irontrack.entity.User;
import com.irontrack.irontrack.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    /**
     * Retorna todos os treinos de um usuário,
     * ordenados pela data e hora agendada (crescente).
     *
     * @param user usuário dono dos treinos
     * @return lista de treinos ordenada por scheduledDateTime
     */
    List<Workout> findByUserOrderByScheduledDataTimeAsc(User user);

    /**
     * Retorna apenas os treinos concluídos de um usuário.
     *
     * @param user usuário dono dos treinos
     * @return lista de treinos finalizados
     */
    List<Workout> findByUserAndIsCompletedTrue(User user);
}
