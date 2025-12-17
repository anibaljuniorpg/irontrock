package com.irontrack.irontrack.dto;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutDTO(Long id, String title, String note, String type, String category, String Duration, List<ExerciseDTO> exercises, LocalDateTime scheduledDateTime, boolean isCompleted) {
}
