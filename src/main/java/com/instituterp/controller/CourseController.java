package com.instituterp.controller;

import com.instituterp.entity.Course;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "Course Management", description = "Course related endpoints")
public class CourseController {

    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieve list of all courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        // TODO: Implement get all courses logic
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieve a specific course by ID")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        // TODO: Implement get course by ID logic
        return ResponseEntity.ok(new Course());
    }

    @PostMapping
    @Operation(summary = "Create new course", description = "Create a new course")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        // TODO: Implement create course logic
        return ResponseEntity.status(HttpStatus.CREATED).body(new Course());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Update existing course")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        // TODO: Implement update course logic
        return ResponseEntity.ok(new Course());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Delete a course")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        // TODO: Implement delete course logic
        return ResponseEntity.noContent().build();
    }
}
