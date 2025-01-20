package com.youcode.aptio.controller;

import com.youcode.aptio.dto.business.*;
import com.youcode.aptio.service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/businesses")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BusinessResponse> createBusiness(@Valid @RequestBody BusinessRequest request) {
        return ResponseEntity.ok(businessService.createBusiness(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS_OWNER')")
    public ResponseEntity<BusinessResponse> updateBusiness(
            @PathVariable Long id,
            @Valid @RequestBody BusinessUpdateRequest request
    ) {
        return ResponseEntity.ok(businessService.updateBusiness(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        businessService.deleteBusiness(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/owner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BusinessResponse> assignOwner(
            @PathVariable Long id,
            @Valid @RequestBody AssignOwnerRequest request
    ) {
        return ResponseEntity.ok(businessService.assignOwner(id, request.getUserId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS_OWNER')")
    public ResponseEntity<BusinessResponse> getBusinessById(@PathVariable Long id) {
        return ResponseEntity.ok(businessService.getBusinessById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BusinessResponse>> getAllBusinesses() {
        return ResponseEntity.ok(businessService.getAllBusinesses());
    }

    @GetMapping("/my-businesses")
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    public ResponseEntity<List<BusinessResponse>> getMyBusinesses() {
        return ResponseEntity.ok(businessService.getMyBusinesses());
    }
}