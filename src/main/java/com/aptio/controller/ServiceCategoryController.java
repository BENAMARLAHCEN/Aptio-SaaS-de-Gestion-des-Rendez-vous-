package com.aptio.controller;

import com.aptio.dto.ServiceCategoryDTO;
import com.aptio.service.ServiceEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/service-categories")
@RequiredArgsConstructor
public class ServiceCategoryController {

    private final ServiceEntityService serviceService;

    @GetMapping
    public ResponseEntity<List<ServiceCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(serviceService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryDTO> getCategoryById(@PathVariable String id) {
        return ResponseEntity.ok(serviceService.getCategoryById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceCategoryDTO> createCategory(@Valid @RequestBody ServiceCategoryDTO categoryDTO) {
        return new ResponseEntity<>(serviceService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceCategoryDTO> updateCategory(@PathVariable String id, @Valid @RequestBody ServiceCategoryDTO categoryDTO) {
        return ResponseEntity.ok(serviceService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        serviceService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceCategoryDTO> toggleCategoryStatus(@PathVariable String id, @RequestBody Map<String, Boolean> statusMap) {
        boolean active = statusMap.getOrDefault("active", true);
        return ResponseEntity.ok(serviceService.toggleCategoryStatus(id, active));
    }
}