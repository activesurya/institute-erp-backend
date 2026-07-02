package com.instituterp.controller;

import com.instituterp.entity.Attendance;
import com.instituterp.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance Management", description = "Attendance related endpoints")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    @Operation(summary = "Get all attendance records", description = "Retrieve all attendance records")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get attendance by ID", description = "Retrieve a specific attendance record by ID")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get attendance for student", description = "Retrieve all attendance records for a student")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/date/{date}")
    @Operation(summary = "Get attendance for student on date", description = "Retrieve attendance record for a student on a specific date")
    public ResponseEntity<Attendance> getAttendanceByStudentAndDate(
            @PathVariable Long studentId,
            @PathVariable String date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudentAndDate(studentId, LocalDate.parse(date)));
    }

    @GetMapping("/batch/{batchId}/date/{date}")
    @Operation(summary = "Get batch attendance on date", description = "Retrieve attendance records for a batch on a specific date")
    public ResponseEntity<List<Attendance>> getAttendanceByBatchAndDate(
            @PathVariable Long batchId,
            @PathVariable String date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByBatchAndDate(batchId, LocalDate.parse(date)));
    }

    @PostMapping
    @Operation(summary = "Create attendance record", description = "Create a new attendance record")
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.createAttendance(attendance));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update attendance record", description = "Update an existing attendance record")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, attendance));
    }

    @PostMapping("/batch/{batchId}/mark-attendance")
    @Operation(summary = "Mark batch attendance", description = "Mark attendance for all students in a batch")
    public ResponseEntity<String> markBatchAttendance(
            @PathVariable Long batchId,
            @RequestParam String date,
            @RequestBody Map<String, List<Long>> payload) {
        attendanceService.markBatchAttendance(batchId, LocalDate.parse(date), payload.get("presentStudentIds"));
        return ResponseEntity.ok("Attendance marked successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete attendance record", description = "Delete an attendance record")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}/percentage")
    @Operation(summary = "Get attendance percentage", description = "Get attendance percentage for a student")
    public ResponseEntity<Map<String, Double>> getAttendancePercentage(@PathVariable Long studentId) {
        double percentage = attendanceService.getAttendancePercentage(studentId);
        return ResponseEntity.ok(Map.of("percentage", percentage));
    }
}
