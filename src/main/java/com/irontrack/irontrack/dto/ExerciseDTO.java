package com.irontrack.irontrack.dto;

import com.irontrack.irontrack.entity.Exercise;

public record ExerciseDTO(Long id, String name, int sets, int reps, double weight) {
    public ExerciseDTO(Exercise exercise) {
        this(
                exercise.getId(),
                exercise.getName(),
                exercise.getSets(),
                exercise.getReps(),
                exercise.getWight()
        );
    }
}
