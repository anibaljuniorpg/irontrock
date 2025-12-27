package com.irontrack.irontrack.entity;

import com.irontrack.irontrack.dto.ExerciseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_exercise")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int sets;
    private int reps;
    private double wight;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public Exercise(ExerciseDTO exerciseDTO) {
    }
}
