package com.youcode.aptio.controller;

import com.youcode.aptio.dto.workingHours.WorkingHoursRequest;
import com.youcode.aptio.dto.workingHours.WorkingHoursResponse;
import com.youcode.aptio.service.WorkingHoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/working-hours")
@RequiredArgsConstructor
public class WorkingHoursController {
    private final WorkingHoursService workingHoursService;

    @PostMapping("/business/{businessId}")
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    public ResponseEntity<WorkingHoursResponse> createWorkingHours(
            @PathVariable Long businessId,
            @Valid @RequestBody WorkingHoursRequest request
    ) {
        return ResponseEntity.ok(workingHoursService.createWorkingHours(businessId, request));
    }

    @PutMapping("/{workingHoursId}")
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    public ResponseEntity<WorkingHoursResponse> updateWorkingHours(
            @PathVariable Long workingHoursId,
            @Valid @RequestBody WorkingHoursRequest request
    ) {
        return ResponseEntity.ok(workingHoursService.updateWorkingHours(workingHoursId, request));
    }

    @DeleteMapping("/{workingHoursId}")
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    public ResponseEntity<Void> deleteWorkingHours(@PathVariable Long workingHoursId) {
        workingHoursService.deleteWorkingHours(workingHoursId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/business/{businessId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<WorkingHoursResponse>> getWorkingHoursByBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(workingHoursService.getWorkingHoursByBusiness(businessId));
    }
}