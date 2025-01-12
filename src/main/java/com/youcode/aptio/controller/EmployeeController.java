package com.youcode.aptio.controller;

import com.youcode.aptio.dto.employee.EmployeeRequest;
import com.youcode.aptio.dto.employee.EmployeeResponse;
import com.youcode.aptio.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> assignEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.assignEmployee(employeeRequest));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByBusiness(@PathVariable String businessId) {
        return ResponseEntity.ok(employeeService.getEmployeesByBusiness(businessId));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployeePosition(@PathVariable String employeeId, @RequestParam String position) {
        return ResponseEntity.ok(employeeService.updateEmployeePosition(employeeId, position));
    }

    @PostMapping("/{employeeId}/block")
    public ResponseEntity<EmployeeResponse> blockEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.blockEmployee(employeeId));
    }

    @PostMapping("/{employeeId}/unblock")
    public ResponseEntity<EmployeeResponse> unblockEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.unblockEmployee(employeeId));
    }
}
