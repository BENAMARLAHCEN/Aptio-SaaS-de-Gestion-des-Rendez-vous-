package com.youcode.aptio.service;

import com.youcode.aptio.dto.employee.EmployeeRequest;
import com.youcode.aptio.dto.employee.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse assignEmployee(EmployeeRequest employeeRequest);

    List<EmployeeResponse> getEmployeesByBusiness(String businessId);

    EmployeeResponse getEmployeeById(String employeeId);

    void deleteEmployee(String employeeId);

    EmployeeResponse updateEmployeePosition(String employeeId, String position);

    EmployeeResponse blockEmployee(String employeeId);

    EmployeeResponse unblockEmployee(String employeeId);
}
