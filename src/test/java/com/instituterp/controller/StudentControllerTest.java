package com.instituterp.controller;

import com.instituterp.dto.StudentDTO;
import com.instituterp.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private StudentDTO testStudentDTO;

    @BeforeEach
    void setUp() {
        testStudentDTO = new StudentDTO();
        testStudentDTO.setId(1L);
        testStudentDTO.setRollNumber("ROLL001");
        testStudentDTO.setAdmissionNumber("ADM001");
        testStudentDTO.setParentName("Parent Name");
    }

    @Test
    void testGetAllStudents() {
        List<StudentDTO> studentList = new ArrayList<>();
        studentList.add(testStudentDTO);
        when(studentService.getAllStudents()).thenReturn(studentList);

        ResponseEntity<List<StudentDTO>> response = studentController.getAllStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testGetStudentById() {
        when(studentService.getStudentById(1L)).thenReturn(testStudentDTO);

        ResponseEntity<StudentDTO> response = studentController.getStudentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ROLL001", response.getBody().getRollNumber());
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testCreateStudent() {
        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(testStudentDTO);

        ResponseEntity<StudentDTO> response = studentController.createStudent(testStudentDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(studentService, times(1)).createStudent(any(StudentDTO.class));
    }

    @Test
    void testUpdateStudent() {
        when(studentService.updateStudent(1L, testStudentDTO)).thenReturn(testStudentDTO);

        ResponseEntity<StudentDTO> response = studentController.updateStudent(1L, testStudentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(studentService, times(1)).updateStudent(1L, testStudentDTO);
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentService).deleteStudent(1L);

        ResponseEntity<Void> response = studentController.deleteStudent(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentService, times(1)).deleteStudent(1L);
    }
}
