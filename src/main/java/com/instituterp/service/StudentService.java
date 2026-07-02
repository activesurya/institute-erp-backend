package com.instituterp.service;

import com.instituterp.dto.StudentDTO;
import com.instituterp.entity.Student;
import com.instituterp.entity.User;
import com.instituterp.exception.BadRequestException;
import com.instituterp.exception.ResourceNotFoundException;
import com.instituterp.repository.StudentRepository;
import com.instituterp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    /**
     * Get all active students
     */
    public List<StudentDTO> getAllStudents() {
        log.info("Fetching all active students");
        return studentRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get student by ID
     */
    public StudentDTO getStudentById(Long id) {
        log.info("Fetching student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return convertToDTO(student);
    }

    /**
     * Get student by roll number
     */
    public StudentDTO getStudentByRollNumber(String rollNumber) {
        log.info("Fetching student with roll number: {}", rollNumber);
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with roll number: " + rollNumber));
        return convertToDTO(student);
    }

    /**
     * Get student by admission number
     */
    public StudentDTO getStudentByAdmissionNumber(String admissionNumber) {
        log.info("Fetching student with admission number: {}", admissionNumber);
        Student student = studentRepository.findByAdmissionNumber(admissionNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with admission number: " + admissionNumber));
        return convertToDTO(student);
    }

    /**
     * Create new student
     */
    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("Creating new student with roll number: {}", studentDTO.getRollNumber());

        // Validate roll number uniqueness
        if (studentRepository.findByRollNumber(studentDTO.getRollNumber()).isPresent()) {
            throw new BadRequestException("Roll number already exists: " + studentDTO.getRollNumber());
        }

        // Validate admission number uniqueness
        if (studentDTO.getAdmissionNumber() != null && 
            studentRepository.findByAdmissionNumber(studentDTO.getAdmissionNumber()).isPresent()) {
            throw new BadRequestException("Admission number already exists: " + studentDTO.getAdmissionNumber());
        }

        Student student = new Student();
        student.setRollNumber(studentDTO.getRollNumber());
        student.setAdmissionNumber(studentDTO.getAdmissionNumber());
        student.setDateOfAdmission(studentDTO.getDateOfAdmission());
        student.setAddress(studentDTO.getAddress());
        student.setParentContact(studentDTO.getParentContact());
        student.setParentName(studentDTO.getParentName());
        student.setIsActive(true);

        // Link user if provided
        if (studentDTO.getUser() != null && studentDTO.getUser().getId() != null) {
            User user = userRepository.findById(studentDTO.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            student.setUser(user);
        }

        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully with ID: {}", savedStudent.getId());

        return convertToDTO(savedStudent);
    }

    /**
     * Update student
     */
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        log.info("Updating student with ID: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        // Check if roll number is being updated
        if (!student.getRollNumber().equals(studentDTO.getRollNumber()) && 
            studentRepository.findByRollNumber(studentDTO.getRollNumber()).isPresent()) {
            throw new BadRequestException("Roll number already exists: " + studentDTO.getRollNumber());
        }

        student.setRollNumber(studentDTO.getRollNumber());
        student.setAdmissionNumber(studentDTO.getAdmissionNumber());
        student.setDateOfAdmission(studentDTO.getDateOfAdmission());
        student.setAddress(studentDTO.getAddress());
        student.setParentContact(studentDTO.getParentContact());
        student.setParentName(studentDTO.getParentName());

        Student updatedStudent = studentRepository.save(student);
        log.info("Student updated successfully with ID: {}", id);

        return convertToDTO(updatedStudent);
    }

    /**
     * Delete student
     */
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        studentRepository.delete(student);
        log.info("Student deleted successfully with ID: {}", id);
    }

    /**
     * Deactivate student
     */
    public void deactivateStudent(Long id) {
        log.info("Deactivating student with ID: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        student.setIsActive(false);
        studentRepository.save(student);
        log.info("Student deactivated successfully with ID: {}", id);
    }

    /**
     * Activate student
     */
    public void activateStudent(Long id) {
        log.info("Activating student with ID: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        student.setIsActive(true);
        studentRepository.save(student);
        log.info("Student activated successfully with ID: {}", id);
    }

    /**
     * Convert Student entity to StudentDTO
     */
    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setRollNumber(student.getRollNumber());
        dto.setAdmissionNumber(student.getAdmissionNumber());
        dto.setDateOfAdmission(student.getDateOfAdmission());
        dto.setAddress(student.getAddress());
        dto.setParentContact(student.getParentContact());
        dto.setParentName(student.getParentName());
        dto.setIsActive(student.getIsActive());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        return dto;
    }
}
