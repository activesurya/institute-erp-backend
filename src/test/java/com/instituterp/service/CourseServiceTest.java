package com.instituterp.service;

import com.instituterp.entity.Course;
import com.instituterp.entity.Department;
import com.instituterp.exception.BadRequestException;
import com.instituterp.exception.ResourceNotFoundException;
import com.instituterp.repository.CourseRepository;
import com.instituterp.repository.DepartmentRepository;
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
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private CourseService courseService;

    private Course testCourse;
    private Department testDepartment;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setId(1L);
        testDepartment.setName("Computer Science");
        testDepartment.setIsActive(true);

        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setName("Java Programming");
        testCourse.setCourseCode("CS101");
        testCourse.setDescription("Introduction to Java");
        testCourse.setCreditHours(3);
        testCourse.setDepartment(testDepartment);
        testCourse.setIsActive(true);
    }

    @Test
    void testGetAllCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(testCourse);
        when(courseRepository.findByIsActiveTrue()).thenReturn(courseList);

        List<Course> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CS101", result.get(0).getCourseCode());
        verify(courseRepository, times(1)).findByIsActiveTrue();
    }

    @Test
    void testGetCourseById_Success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));

        Course result = courseService.getCourseById(1L);

        assertNotNull(result);
        assertEquals("CS101", result.getCourseCode());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCourseById_NotFound() {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.getCourseById(999L));
    }

    @Test
    void testGetCourseByCourseCode_Success() {
        when(courseRepository.findByCourseCode("CS101")).thenReturn(Optional.of(testCourse));

        Course result = courseService.getCourseByCourseCode("CS101");

        assertNotNull(result);
        assertEquals("CS101", result.getCourseCode());
        verify(courseRepository, times(1)).findByCourseCode("CS101");
    }

    @Test
    void testCreateCourse_Success() {
        Course newCourse = new Course();
        newCourse.setName("Python Programming");
        newCourse.setCourseCode("CS102");
        newCourse.setDepartment(testDepartment);

        when(courseRepository.findByCourseCode("CS102")).thenReturn(Optional.empty());
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(testDepartment));
        when(courseRepository.save(any(Course.class))).thenReturn(newCourse);

        Course result = courseService.createCourse(newCourse);

        assertNotNull(result);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testCreateCourse_CourseCodeAlreadyExists() {
        Course newCourse = new Course();
        newCourse.setCourseCode("CS101");

        when(courseRepository.findByCourseCode("CS101")).thenReturn(Optional.of(testCourse));

        assertThrows(BadRequestException.class, () -> courseService.createCourse(newCourse));
    }

    @Test
    void testUpdateCourse_Success() {
        Course updateCourse = new Course();
        updateCourse.setName("Advanced Java");
        updateCourse.setCourseCode("CS101");
        updateCourse.setDepartment(testDepartment);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        Course result = courseService.updateCourse(1L, updateCourse);

        assertNotNull(result);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testDeleteCourse_Success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).delete(testCourse);
    }
}
