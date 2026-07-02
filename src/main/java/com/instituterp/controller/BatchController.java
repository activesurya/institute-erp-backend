package com.instituterp.controller;

import com.instituterp.entity.Batch;
import com.instituterp.service.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batches")
@RequiredArgsConstructor
@Tag(name = "Batch Management", description = "Batch related endpoints")
public class BatchController {

    private final BatchService batchService;

    @GetMapping
    @Operation(summary = "Get all batches", description = "Retrieve list of all active batches")
    public ResponseEntity<List<Batch>> getAllBatches() {
        return ResponseEntity.ok(batchService.getAllBatches());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get batch by ID", description = "Retrieve a specific batch by ID")
    public ResponseEntity<Batch> getBatchById(@PathVariable Long id) {
        return ResponseEntity.ok(batchService.getBatchById(id));
    }

    @GetMapping("/code/{batchCode}")
    @Operation(summary = "Get batch by code", description = "Retrieve a batch by batch code")
    public ResponseEntity<Batch> getBatchByCode(@PathVariable String batchCode) {
        return ResponseEntity.ok(batchService.getBatchByBatchCode(batchCode));
    }

    @PostMapping
    @Operation(summary = "Create new batch", description = "Create a new batch")
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        return ResponseEntity.status(HttpStatus.CREATED).body(batchService.createBatch(batch));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update batch", description = "Update existing batch")
    public ResponseEntity<Batch> updateBatch(@PathVariable Long id, @RequestBody Batch batch) {
        return ResponseEntity.ok(batchService.updateBatch(id, batch));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete batch", description = "Delete a batch")
    public ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        batchService.deleteBatch(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate batch", description = "Deactivate a batch")
    public ResponseEntity<String> deactivateBatch(@PathVariable Long id) {
        batchService.deactivateBatch(id);
        return ResponseEntity.ok("Batch deactivated successfully");
    }
}
