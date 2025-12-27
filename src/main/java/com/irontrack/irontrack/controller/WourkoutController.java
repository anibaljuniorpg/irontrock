package com.irontrack.irontrack.controller;

import com.irontrack.irontrack.dto.WorkoutDTO;
import com.irontrack.irontrack.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WourkoutController {
    @Autowired
    private WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<WorkoutDTO> createWorkout(@RequestBody WorkoutDTO request){
        return ResponseEntity.ok(workoutService.createWorkout(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> updateWorkout(@PathVariable Long id, @RequestBody WorkoutDTO request){
        return ResponseEntity.ok(workoutService.updateWorkout(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id){
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> listWorkouts(){return ResponseEntity.ok(workoutService.listWorkouts());}

    @GetMapping("/report")
    public ResponseEntity<String> generateReport(){return ResponseEntity.ok(workoutService.generateReport());}
}
