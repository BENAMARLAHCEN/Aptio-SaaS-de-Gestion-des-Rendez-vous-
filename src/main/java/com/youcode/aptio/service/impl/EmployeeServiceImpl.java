package com.youcode.aptio.service.impl;

import com.youcode.aptio.dto.employee.EmployeeRequest;
import com.youcode.aptio.dto.employee.EmployeeResponse;
import com.youcode.aptio.repository.BusinessRepository;
import com.youcode.aptio.repository.EmployeeRepository;
import com.youcode.aptio.repository.UserRepository;
import com.youcode.aptio.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;


    @Override
    public EmployeeResponse assignEmployee(EmployeeRequest employeeRequest) {
        return null;
    }

    @Override
    public List<EmployeeResponse> getEmployeesByBusiness(String businessId) {
        return List.of();
    }

    @Override
    public EmployeeResponse getEmployeeById(String employeeId) {
        return null;
    }

    @Override
    public void deleteEmployee(String employeeId) {

    }

    @Override
    public EmployeeResponse updateEmployeePosition(String employeeId, String position) {
        return null;
    }

    @Override
    public EmployeeResponse blockEmployee(String employeeId) {
        return null;
    }

    @Override
    public EmployeeResponse unblockEmployee(String employeeId) {
        return null;
    }
}
