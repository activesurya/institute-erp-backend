package com.instituterp.controller;

import com.instituterp.dto.StudentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Student Management", description = "Student related endpoints")
public class StudentController {

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve list of all students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        // TODO: Implement get all students logic
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a specific student by ID")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        // TODO: Implement get student by ID logic
        return ResponseEntity.ok(new StudentDTO());
    }

    @PostMapping
    @Operation(summary = "Create new student", description = "Create a new student record")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        // TODO: Implement create student logic
        return ResponseEntity.status(HttpStatus.CREATED).body(new StudentDTO());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Update existing student record")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        // TODO: Implement update student logic
        return ResponseEntity.ok(new StudentDTO());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Delete a student record")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        // TODO: Implement delete student logic
        return ResponseEntity.noContent().build();
    }
}
