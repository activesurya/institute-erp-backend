package com.instituterp.service;

import com.instituterp.dto.StudentDTO;
import com.instituterp.entity.Student;
import com.instituterp.entity.User;
import com.instituterp.exception.BadRequestException;
import com.instituterp.exception.ResourceNotFoundException;
import com.instituterp.repository.StudentRepository;
import com.instituterp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudentService studentService;

    private Student testStudent;
    private StudentDTO testStudentDTO;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setRollNumber("ROLL001");
        testStudent.setAdmissionNumber("ADM001");
        testStudent.setDateOfAdmission("2023-01-01");
        testStudent.setAddress("123 Main St");
        testStudent.setParentContact("9876543210");
        testStudent.setParentName("Parent Name");
        testStudent.setIsActive(true);

        testStudentDTO = new StudentDTO();
        testStudentDTO.setId(1L);
        testStudentDTO.setRollNumber("ROLL001");
        testStudentDTO.setAdmissionNumber("ADM001");
    }

    @Test
    void testGetAllStudents() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(testStudent);
        when(studentRepository.findByIsActiveTrue()).thenReturn(studentList);

        List<StudentDTO> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ROLL001", result.get(0).getRollNumber());
        verify(studentRepository, times(1)).findByIsActiveTrue();
    }

    @Test
    void testGetStudentById_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        StudentDTO result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals("ROLL001", result.getRollNumber());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(999L));
    }

    @Test
    void testGetStudentByRollNumber_Success() {
        when(studentRepository.findByRollNumber("ROLL001")).thenReturn(Optional.of(testStudent));

        StudentDTO result = studentService.getStudentByRollNumber("ROLL001");

        assertNotNull(result);
        assertEquals("ROLL001", result.getRollNumber());
        verify(studentRepository, times(1)).findByRollNumber("ROLL001");
    }

    @Test
    void testGetStudentByRollNumber_NotFound() {
        when(studentRepository.findByRollNumber("NONEXISTENT")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentByRollNumber("NONEXISTENT"));
    }

    @Test
    void testCreateStudent_Success() {
        StudentDTO newStudentDTO = new StudentDTO();
        newStudentDTO.setRollNumber("ROLL002");
        newStudentDTO.setAdmissionNumber("ADM002");

        when(studentRepository.findByRollNumber("ROLL002")).thenReturn(Optional.empty());
        when(studentRepository.findByAdmissionNumber("ADM002")).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        StudentDTO result = studentService.createStudent(newStudentDTO);

        assertNotNull(result);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testCreateStudent_RollNumberAlreadyExists() {
        StudentDTO newStudentDTO = new StudentDTO();
        newStudentDTO.setRollNumber("ROLL001");

        when(studentRepository.findByRollNumber("ROLL001")).thenReturn(Optional.of(testStudent));

        assertThrows(BadRequestException.class, () -> studentService.createStudent(newStudentDTO));
    }

    @Test
    void testUpdateStudent_Success() {
        StudentDTO updateDTO = new StudentDTO();
        updateDTO.setRollNumber("ROLL001");
        updateDTO.setParentName("Updated Parent");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        StudentDTO result = studentService.updateStudent(1L, updateDTO);

        assertNotNull(result);
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).delete(testStudent);
    }

    @Test
    void testDeactivateStudent_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        studentService.deactivateStudent(1L);

        verify(studentRepository, times(1)).save(any(Student.class));
    }
}
