package com.youcode.aptio.service;

import com.youcode.aptio.dto.employee.EmployeeRequest;
import com.youcode.aptio.dto.employee.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(Long businessId, EmployeeRequest request);
    EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest request);
    void deleteEmployee(Long employeeId);
    EmployeeResponse getEmployeeById(Long employeeId);
    List<EmployeeResponse> getEmployeesByBusiness(Long businessId);
    List<EmployeeResponse> getActiveEmployeesByBusiness(Long businessId);
    List<EmployeeResponse> searchEmployeesByPosition(Long businessId, String position);
    boolean isUserEmployeeOfBusiness(Long businessId, Long userId);
}