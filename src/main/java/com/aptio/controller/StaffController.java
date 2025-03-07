package com.aptio.controller;

import com.aptio.dto.StaffDTO;
import com.aptio.dto.WorkHoursDTO;
import com.aptio.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<List<StaffDTO>> getAllStaff(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String specialty) {

        if (specialty != null && !specialty.isEmpty()) {
            return ResponseEntity.ok(staffService.getStaffBySpecialty(specialty));
        } else if (active != null && active) {
            return ResponseEntity.ok(staffService.getActiveStaff());
        } else {
            return ResponseEntity.ok(staffService.getAllStaff());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable String id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StaffDTO> createStaff(@Valid @RequestBody StaffDTO staffDTO) {
        return new ResponseEntity<>(staffService.createStaff(staffDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StaffDTO> updateStaff(@PathVariable String id, @Valid @RequestBody StaffDTO staffDTO) {
        return ResponseEntity.ok(staffService.updateStaff(id, staffDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteStaff(@PathVariable String id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StaffDTO> toggleStaffStatus(@PathVariable String id, @RequestBody Map<String, Boolean> statusMap) {
        boolean active = statusMap.getOrDefault("active", true);
        return ResponseEntity.ok(staffService.toggleStaffStatus(id, active));
    }

    @PutMapping("/{id}/schedule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StaffDTO> updateWorkSchedule(
            @PathVariable String id,
            @RequestBody Map<String, List<WorkHoursDTO>> scheduleMap) {

        List<WorkHoursDTO> workHours = scheduleMap.get("workHours");
        return ResponseEntity.ok(staffService.updateWorkHours(id, workHours));
    }
}