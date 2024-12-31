package com.youcode.aptio.controller;

import com.youcode.aptio.dto.business.BusinessRequest;
import com.youcode.aptio.dto.business.BusinessResponse;
import com.youcode.aptio.dto.workingHours.WorkingHoursRequest;
import com.youcode.aptio.service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BusinessResponse> createBusiness(@RequestBody @Valid BusinessRequest businessRequest) {
        return ResponseEntity.ok(businessService.createBusiness(businessRequest));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BusinessResponse> getBusinessById(@PathVariable Long id) {
//        return ResponseEntity.ok(businessService.getBusinessById(id));
//    }

    @GetMapping("/{name}")
    public ResponseEntity<BusinessResponse> getBusinessByName(@PathVariable String name) {
        return ResponseEntity.ok(businessService.getBusinessByName(name));
    }

    @GetMapping
    public ResponseEntity<List<BusinessResponse>> getAllBusinesses(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(businessService.getAllBusinesses(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessResponse> updateBusiness(@PathVariable Long id, @RequestBody BusinessRequest businessRequest) {
        return ResponseEntity.ok(businessService.updateBusiness(id, businessRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        businessService.deleteBusiness(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/add-employee/{businessId}/{employeeId}")
//    public ResponseEntity<> addEmployeeToBusiness(@PathVariable Long businessId, @PathVariable Long employeeId) {
//        return ResponseEntity.ok(businessService.addEmployeeToBusiness(businessId, employeeId));
//    }
//
//    @DeleteMapping("/remove-employee/{businessId}/{employeeId}")
//    public ResponseEntity<BusinessResponse> removeEmployeeFromBusiness(@PathVariable Long businessId, @PathVariable Long employeeId) {
//        return ResponseEntity.ok(businessService.removeEmployeeFromBusiness(businessId, employeeId));
//    }
//
//    @GetMapping("/employees/{businessId}")
//    public ResponseEntity<BusinessResponse> getEmployeesByBusiness(@PathVariable Long businessId) {
//        return ResponseEntity.ok(businessService.getEmployeesByBusiness(businessId));
//    }

//    @PostMapping("/{id}/working-hours")
//    public ResponseEntity<BusinessResponse> addWorkingHoursToBusiness(@PathVariable Long id, @RequestBody WorkingHoursRequest workingHoursRequest) {
//        return ResponseEntity.ok(businessService.addWorkingHoursToBusiness(id, workingHoursRequest));
//    }
//
//    @DeleteMapping("/{id}/working-hours/{workingHoursId}")
//    public ResponseEntity<BusinessResponse> removeWorkingHoursFromBusiness(@PathVariable Long id, @PathVariable Long workingHoursId) {
//        return ResponseEntity.ok(businessService.removeWorkingHoursFromBusiness(id, workingHoursId));
//    }
//
//    @GetMapping("/{id}/working-hours")
//    public ResponseEntity<BusinessResponse> getWorkingHoursByBusiness(@PathVariable Long id) {
//        return ResponseEntity.ok(businessService.getWorkingHoursByBusiness(id));
//    }
//
//    @GetMapping("/working-hours/{day}")
//    public ResponseEntity<BusinessResponse> getBusinessesByWorkingDay(@PathVariable String day) {
//        return ResponseEntity.ok(businessService.getBusinessesByWorkingDay(day));
//    }



}
