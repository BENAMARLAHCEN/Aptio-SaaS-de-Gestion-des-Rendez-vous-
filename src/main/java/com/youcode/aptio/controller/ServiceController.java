package com.youcode.aptio.controller;

import com.youcode.aptio.dto.service.ServiceRequest;
import com.youcode.aptio.dto.service.ServiceResponse;
import com.youcode.aptio.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @PostMapping("/business/{businessId}")
    public ResponseEntity<ServiceResponse> createService(@PathVariable Long businessId, @RequestBody ServiceRequest serviceRequest) {
        ServiceResponse serviceResponse = serviceService.createService(businessId, serviceRequest);
        return ResponseEntity.ok(serviceResponse);
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<ServiceResponse>> getServicesByBusiness(@PathVariable Long businessId) {
        List<ServiceResponse> serviceResponses = serviceService.getServicesByBusinessId(businessId);
        return ResponseEntity.ok(serviceResponses);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable Long serviceId) {
        ServiceResponse serviceResponse = serviceService.getServiceById(serviceId);
        return ResponseEntity.ok(serviceResponse);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable Long serviceId, @RequestBody ServiceRequest serviceRequest) {
        ServiceResponse serviceResponse = serviceService.updateService(serviceId, serviceRequest);
        return ResponseEntity.ok(serviceResponse);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }
}
