package com.instituterp.service;

import com.instituterp.entity.Department;
import com.instituterp.exception.BadRequestException;
import com.instituterp.exception.ResourceNotFoundException;
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
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    /**
     * Get all active departments
     */
    public List<Department> getAllDepartments() {
        log.info("Fetching all active departments");
        return departmentRepository.findByIsActiveTrue();
    }

    /**
     * Get department by ID
     */
    public Department getDepartmentById(Long id) {
        log.info("Fetching department with ID: {}", id);
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
    }

    /**
     * Get department by name
     */
    public Department getDepartmentByName(String name) {
        log.info("Fetching department with name: {}", name);
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with name: " + name));
    }

    /**
     * Get department by code
     */
    public Department getDepartmentByCode(String code) {
        log.info("Fetching department with code: {}", code);
        return departmentRepository.findByDepartmentCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with code: " + code));
    }

    /**
     * Create new department
     */
    public Department createDepartment(Department department) {
        log.info("Creating new department with name: {}", department.getName());

        // Validate name uniqueness
        if (departmentRepository.findByName(department.getName()).isPresent()) {
            throw new BadRequestException("Department name already exists: " + department.getName());
        }

        // Validate department code uniqueness
        if (department.getDepartmentCode() != null && 
            departmentRepository.findByDepartmentCode(department.getDepartmentCode()).isPresent()) {
            throw new BadRequestException("Department code already exists: " + department.getDepartmentCode());
        }

        department.setIsActive(true);
        Department savedDepartment = departmentRepository.save(department);
        log.info("Department created successfully with ID: {}", savedDepartment.getId());

        return savedDepartment;
    }

    /**
     * Update department
     */
    public Department updateDepartment(Long id, Department departmentDetails) {
        log.info("Updating department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));

        // Check if name is being updated
        if (!department.getName().equals(departmentDetails.getName()) && 
            departmentRepository.findByName(departmentDetails.getName()).isPresent()) {
            throw new BadRequestException("Department name already exists: " + departmentDetails.getName());
        }

        // Check if code is being updated
        if (departmentDetails.getDepartmentCode() != null && 
            !department.getDepartmentCode().equals(departmentDetails.getDepartmentCode()) &&
            departmentRepository.findByDepartmentCode(departmentDetails.getDepartmentCode()).isPresent()) {
            throw new BadRequestException("Department code already exists: " + departmentDetails.getDepartmentCode());
        }

        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        department.setDepartmentCode(departmentDetails.getDepartmentCode());
        department.setHodId(departmentDetails.getHodId());

        Department updatedDepartment = departmentRepository.save(department);
        log.info("Department updated successfully with ID: {}", id);

        return updatedDepartment;
    }

    /**
     * Delete department
     */
    public void deleteDepartment(Long id) {
        log.info("Deleting department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));

        departmentRepository.delete(department);
        log.info("Department deleted successfully with ID: {}", id);
    }

    /**
     * Deactivate department
     */
    public void deactivateDepartment(Long id) {
        log.info("Deactivating department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));

        department.setIsActive(false);
        departmentRepository.save(department);
        log.info("Department deactivated successfully with ID: {}", id);
    }
}
