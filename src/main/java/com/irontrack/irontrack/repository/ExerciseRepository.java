package com.irontrack.irontrack.repository;

import com.irontrack.irontrack.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ExerciseRepository extends JpaRepository<Exercise, Long> {
}
