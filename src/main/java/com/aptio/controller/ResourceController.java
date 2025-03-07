package com.aptio.controller;

import com.aptio.dto.ResourceDTO;
import com.aptio.service.ResourceService;
import com.aptio.util.TimeUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<List<ResourceDTO>> getAllResources(
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String type) {

        if (type != null && !type.isEmpty()) {
            return ResponseEntity.ok(resourceService.getResourcesByType(type));
        } else if (available != null && available) {
            return ResponseEntity.ok(resourceService.getAvailableResources());
        } else {
            return ResponseEntity.ok(resourceService.getAllResources());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<ResourceDTO> getResourceById(@PathVariable String id) {
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResourceDTO> createResource(@Valid @RequestBody ResourceDTO resourceDTO) {
        return new ResponseEntity<>(resourceService.createResource(resourceDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResourceDTO> updateResource(
            @PathVariable String id,
            @Valid @RequestBody ResourceDTO resourceDTO) {

        return ResponseEntity.ok(resourceService.updateResource(id, resourceDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteResource(@PathVariable String id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/availability")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResourceDTO> toggleResourceAvailability(
            @PathVariable String id,
            @RequestBody Map<String, Boolean> availabilityMap) {

        boolean isAvailable = availabilityMap.getOrDefault("available", true);
        return ResponseEntity.ok(resourceService.toggleResourceAvailability(id, isAvailable));
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<List<ResourceDTO>> getAvailableResourcesForTimeSlot(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String startTime,
            @RequestParam String endTime) {

        LocalTime start = TimeUtils.parseTime(startTime);
        LocalTime end = TimeUtils.parseTime(endTime);

        return ResponseEntity.ok(resourceService.getAvailableResourcesForTimeSlot(date, start, end));
    }
}