package com.instituterp.service;

import com.instituterp.entity.Attendance;
import com.instituterp.entity.Student;
import com.instituterp.entity.Batch;
import com.instituterp.exception.ResourceNotFoundException;
import com.instituterp.repository.AttendanceRepository;
import com.instituterp.repository.StudentRepository;
import com.instituterp.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final BatchRepository batchRepository;

    /**
     * Get all attendance records
     */
    public List<Attendance> getAllAttendance() {
        log.info("Fetching all attendance records");
        return attendanceRepository.findAll();
    }

    /**
     * Get attendance by ID
     */
    public Attendance getAttendanceById(Long id) {
        log.info("Fetching attendance with ID: {}", id);
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found with ID: " + id));
    }

    /**
     * Get attendance for a student on a specific date
     */
    public Attendance getAttendanceByStudentAndDate(Long studentId, LocalDate date) {
        log.info("Fetching attendance for student ID: {} on date: {}", studentId, date);
        return attendanceRepository.findByStudentIdAndAttendanceDate(studentId, date)
                .orElseThrow(() -> new ResourceNotFoundException("No attendance record found"));
    }

    /**
     * Get all attendance records for a student
     */
    public List<Attendance> getAttendanceByStudent(Long studentId) {
        log.info("Fetching all attendance records for student ID: {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return attendanceRepository.findByStudent(student);
    }

    /**
     * Get all attendance records for a batch on a specific date
     */
    public List<Attendance> getAttendanceByBatchAndDate(Long batchId, LocalDate date) {
        log.info("Fetching attendance for batch ID: {} on date: {}", batchId, date);
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));
        return attendanceRepository.findByBatchAndAttendanceDate(batch, date);
    }

    /**
     * Create new attendance record
     */
    public Attendance createAttendance(Attendance attendance) {
        log.info("Creating new attendance record for student ID: {}", attendance.getStudent().getId());

        // Validate student exists
        Student student = studentRepository.findById(attendance.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        attendance.setStudent(student);

        // Validate batch exists
        Batch batch = batchRepository.findById(attendance.getBatch().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));
        attendance.setBatch(batch);

        // Set attendance date to today if not provided
        if (attendance.getAttendanceDate() == null) {
            attendance.setAttendanceDate(LocalDate.now());
        }

        Attendance savedAttendance = attendanceRepository.save(attendance);
        log.info("Attendance record created successfully with ID: {}", savedAttendance.getId());

        return savedAttendance;
    }

    /**
     * Update attendance record
     */
    public Attendance updateAttendance(Long id, Attendance attendanceDetails) {
        log.info("Updating attendance record with ID: {}", id);

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found with ID: " + id));

        attendance.setIsPresent(attendanceDetails.getIsPresent());
        attendance.setRemarks(attendanceDetails.getRemarks());

        Attendance updatedAttendance = attendanceRepository.save(attendance);
        log.info("Attendance record updated successfully with ID: {}", id);

        return updatedAttendance;
    }

    /**
     * Mark attendance for batch
     */
    public void markBatchAttendance(Long batchId, LocalDate date, List<Long> presentStudentIds) {
        log.info("Marking attendance for batch ID: {} on date: {}", batchId, date);

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));

        // Get all students in batch
        List<Student> students = batch.getEnrollments().stream()
                .map(e -> e.getStudent())
                .toList();

        // Mark attendance for each student
        for (Student student : students) {
            boolean isPresent = presentStudentIds.contains(student.getId());
            
            // Check if attendance already exists
            var existingAttendance = attendanceRepository.findByStudentIdAndAttendanceDate(student.getId(), date);
            
            if (existingAttendance.isPresent()) {
                existingAttendance.get().setIsPresent(isPresent);
                attendanceRepository.save(existingAttendance.get());
            } else {
                Attendance attendance = new Attendance();
                attendance.setStudent(student);
                attendance.setBatch(batch);
                attendance.setAttendanceDate(date);
                attendance.setIsPresent(isPresent);
                attendanceRepository.save(attendance);
            }
        }

        log.info("Attendance marked successfully for batch ID: {} on date: {}", batchId, date);
    }

    /**
     * Delete attendance record
     */
    public void deleteAttendance(Long id) {
        log.info("Deleting attendance record with ID: {}", id);

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found with ID: " + id));

        attendanceRepository.delete(attendance);
        log.info("Attendance record deleted successfully with ID: {}", id);
    }

    /**
     * Get attendance percentage for a student
     */
    public double getAttendancePercentage(Long studentId) {
        log.info("Calculating attendance percentage for student ID: {}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        List<Attendance> records = attendanceRepository.findByStudent(student);
        
        if (records.isEmpty()) {
            return 0.0;
        }

        long presentDays = records.stream()
                .filter(Attendance::getIsPresent)
                .count();

        return (presentDays * 100.0) / records.size();
    }
}
