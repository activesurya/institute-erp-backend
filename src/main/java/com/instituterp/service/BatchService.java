package com.instituterp.service;

import com.instituterp.entity.Batch;
import com.instituterp.entity.Course;
import com.instituterp.exception.BadRequestException;
import com.instituterp.exception.ResourceNotFoundException;
import com.instituterp.repository.BatchRepository;
import com.instituterp.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BatchService {

    private final BatchRepository batchRepository;
    private final CourseRepository courseRepository;

    /**
     * Get all active batches
     */
    public List<Batch> getAllBatches() {
        log.info("Fetching all active batches");
        return batchRepository.findByIsActiveTrue();
    }

    /**
     * Get batch by ID
     */
    public Batch getBatchById(Long id) {
        log.info("Fetching batch with ID: {}", id);
        return batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + id));
    }

    /**
     * Get batch by batch code
     */
    public Batch getBatchByBatchCode(String batchCode) {
        log.info("Fetching batch with code: {}", batchCode);
        return batchRepository.findByBatchCode(batchCode)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with code: " + batchCode));
    }

    /**
     * Create new batch
     */
    public Batch createBatch(Batch batch) {
        log.info("Creating new batch with code: {}", batch.getBatchCode());

        // Validate batch code uniqueness
        if (batchRepository.findByBatchCode(batch.getBatchCode()).isPresent()) {
            throw new BadRequestException("Batch code already exists: " + batch.getBatchCode());
        }

        // Validate dates
        if (batch.getStartDate() != null && batch.getEndDate() != null && 
            batch.getStartDate().isAfter(batch.getEndDate())) {
            throw new BadRequestException("Start date cannot be after end date");
        }

        // Validate course exists
        if (batch.getCourse() != null && batch.getCourse().getId() != null) {
            Course course = courseRepository.findById(batch.getCourse().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            batch.setCourse(course);
        }

        batch.setIsActive(true);
        Batch savedBatch = batchRepository.save(batch);
        log.info("Batch created successfully with ID: {}", savedBatch.getId());

        return savedBatch;
    }

    /**
     * Update batch
     */
    public Batch updateBatch(Long id, Batch batchDetails) {
        log.info("Updating batch with ID: {}", id);

        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + id));

        // Check if batch code is being updated
        if (!batch.getBatchCode().equals(batchDetails.getBatchCode()) && 
            batchRepository.findByBatchCode(batchDetails.getBatchCode()).isPresent()) {
            throw new BadRequestException("Batch code already exists: " + batchDetails.getBatchCode());
        }

        // Validate dates
        if (batchDetails.getStartDate() != null && batchDetails.getEndDate() != null && 
            batchDetails.getStartDate().isAfter(batchDetails.getEndDate())) {
            throw new BadRequestException("Start date cannot be after end date");
        }

        batch.setBatchCode(batchDetails.getBatchCode());
        batch.setBatchName(batchDetails.getBatchName());
        batch.setStartDate(batchDetails.getStartDate());
        batch.setEndDate(batchDetails.getEndDate());
        batch.setTotalStudents(batchDetails.getTotalStudents());

        // Update course if provided
        if (batchDetails.getCourse() != null && batchDetails.getCourse().getId() != null) {
            Course course = courseRepository.findById(batchDetails.getCourse().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            batch.setCourse(course);
        }

        Batch updatedBatch = batchRepository.save(batch);
        log.info("Batch updated successfully with ID: {}", id);

        return updatedBatch;
    }

    /**
     * Delete batch
     */
    public void deleteBatch(Long id) {
        log.info("Deleting batch with ID: {}", id);

        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + id));

        batchRepository.delete(batch);
        log.info("Batch deleted successfully with ID: {}", id);
    }

    /**
     * Deactivate batch
     */
    public void deactivateBatch(Long id) {
        log.info("Deactivating batch with ID: {}", id);

        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + id));

        batch.setIsActive(false);
        batchRepository.save(batch);
        log.info("Batch deactivated successfully with ID: {}", id);
    }
}
