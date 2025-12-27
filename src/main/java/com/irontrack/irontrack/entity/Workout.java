package com.irontrack.irontrack.entity;

import com.irontrack.irontrack.dto.WorkoutDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "t_workout")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String note;
    private String text;
    private String type;
    private String category;
    private String duration_minutes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workout_id")
    private List<Exercise> exercises = new ArrayList<>();

    private LocalDateTime scheduledDataTime;
    private boolean isCompleted;



}
