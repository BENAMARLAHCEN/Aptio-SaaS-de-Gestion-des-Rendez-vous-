package com.youcode.aptio.controller;

import com.youcode.aptio.dto.service.ServiceRequest;
import com.youcode.aptio.dto.service.ServiceResponse;
import com.youcode.aptio.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @PostMapping("/business/{businessId}")
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    public ResponseEntity<ServiceResponse> createService(
            @PathVariable Long businessId,
            @Valid @RequestBody ServiceRequest serviceRequest
    ) {
        return ResponseEntity.ok(serviceService.createService(businessId, serviceRequest));
    }

    @GetMapping("/business/{businessId}")
    @PreAuthorize("hasAnyRole('BUSINESS_OWNER', 'EMPLOYEE')")
    public ResponseEntity<Page<ServiceResponse>> getServicesByBusiness(
            @PathVariable Long businessId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(serviceService.getServicesByBusinessId(businessId, pageable));
    }

    @GetMapping("/business/{businessId}/active")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<ServiceResponse>> getActiveServicesByBusiness(
            @PathVariable Long businessId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(serviceService.getActiveServicesByBusiness(businessId, pageable));
    }

    @GetMapping("/business/{businessId}/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<ServiceResponse>> searchServices(
            @PathVariable Long businessId,
            @RequestParam String query,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(serviceService.searchServices(businessId, query, pageable));
    }

    @GetMapping("/{serviceId}")
    @PreAuthorize("hasAnyRole('BUSINESS_OWNER', 'EMPLOYEE')")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable Long serviceId) {
        return ResponseEntity.ok(serviceService.getServiceById(serviceId));
    }

    @PutMapping("/{serviceId}")
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    public ResponseEntity<ServiceResponse> updateService(
            @PathVariable Long serviceId,
            @Valid @RequestBody ServiceRequest serviceRequest
    ) {
        return ResponseEntity.ok(serviceService.updateService(serviceId, serviceRequest));
    }

    @DeleteMapping("/{serviceId}")
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{serviceId}/toggle-status")
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    public ResponseEntity<ServiceResponse> toggleServiceStatus(@PathVariable Long serviceId) {
        return ResponseEntity.ok(serviceService.toggleServiceStatus(serviceId));
    }
}