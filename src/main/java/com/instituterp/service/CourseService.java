package com.instituterp.service;

import com.instituterp.entity.Course;
import com.instituterp.entity.Department;
import com.instituterp.exception.BadRequestException;
import com.instituterp.exception.ResourceNotFoundException;
import com.instituterp.repository.CourseRepository;
import com.instituterp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * Get all active courses
     */
    public List<Course> getAllCourses() {
        log.info("Fetching all active courses");
        return courseRepository.findByIsActiveTrue();
    }

    /**
     * Get course by ID
     */
    public Course getCourseById(Long id) {
        log.info("Fetching course with ID: {}", id);
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
    }

    /**
     * Get course by course code
     */
    public Course getCourseByCourseCode(String courseCode) {
        log.info("Fetching course with code: {}", courseCode);
        return courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with code: " + courseCode));
    }

    /**
     * Create new course
     */
    public Course createCourse(Course course) {
        log.info("Creating new course with code: {}", course.getCourseCode());

        // Validate course code uniqueness
        if (courseRepository.findByCourseCode(course.getCourseCode()).isPresent()) {
            throw new BadRequestException("Course code already exists: " + course.getCourseCode());
        }

        // Validate department exists
        if (course.getDepartment() != null && course.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(course.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            course.setDepartment(department);
        }

        course.setIsActive(true);
        Course savedCourse = courseRepository.save(course);
        log.info("Course created successfully with ID: {}", savedCourse.getId());

        return savedCourse;
    }

    /**
     * Update course
     */
    public Course updateCourse(Long id, Course courseDetails) {
        log.info("Updating course with ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));

        // Check if course code is being updated
        if (!course.getCourseCode().equals(courseDetails.getCourseCode()) && 
            courseRepository.findByCourseCode(courseDetails.getCourseCode()).isPresent()) {
            throw new BadRequestException("Course code already exists: " + courseDetails.getCourseCode());
        }

        course.setName(courseDetails.getName());
        course.setCourseCode(courseDetails.getCourseCode());
        course.setDescription(courseDetails.getDescription());
        course.setCreditHours(courseDetails.getCreditHours());
        course.setTotalHours(courseDetails.getTotalHours());

        // Update department if provided
        if (courseDetails.getDepartment() != null && courseDetails.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(courseDetails.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            course.setDepartment(department);
        }

        Course updatedCourse = courseRepository.save(course);
        log.info("Course updated successfully with ID: {}", id);

        return updatedCourse;
    }

    /**
     * Delete course
     */
    public void deleteCourse(Long id) {
        log.info("Deleting course with ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));

        courseRepository.delete(course);
        log.info("Course deleted successfully with ID: {}", id);
    }

    /**
     * Deactivate course
     */
    public void deactivateCourse(Long id) {
        log.info("Deactivating course with ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));

        course.setIsActive(false);
        courseRepository.save(course);
        log.info("Course deactivated successfully with ID: {}", id);
    }
}
