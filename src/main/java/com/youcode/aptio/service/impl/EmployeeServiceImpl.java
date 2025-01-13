package com.youcode.aptio.service.impl;

import com.youcode.aptio.dto.employee.EmployeeRequest;
import com.youcode.aptio.dto.employee.EmployeeResponse;
import com.youcode.aptio.exception.ResourceNotFoundException;
import com.youcode.aptio.exception.UnauthorizedAccessException;
import com.youcode.aptio.model.Business;
import com.youcode.aptio.model.Employee;
import com.youcode.aptio.model.Role;
import com.youcode.aptio.model.User;
import com.youcode.aptio.repository.BusinessRepository;
import com.youcode.aptio.repository.EmployeeRepository;
import com.youcode.aptio.repository.RoleRepository;
import com.youcode.aptio.repository.UserRepository;
import com.youcode.aptio.service.EmployeeService;
import com.youcode.aptio.util.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeResponse createEmployee(Long businessId, EmployeeRequest request) {
        Business business = verifyBusinessAndOwnership(businessId);

        // Get the user to be made employee
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if user is already an employee
        if (employeeRepository.findByUserId(user.getId()).isPresent()) {
            throw new IllegalStateException("User is already an employee at another business");
        }

        // Get employee role
        Role employeeRole = roleRepository.findByName("ROLE_EMPLOYEE")
                .orElseThrow(() -> new ResourceNotFoundException("Employee role not found"));

        // Set user's role to employee
        user.setRole(employeeRole);
        userRepository.save(user);

        // Create employee
        Employee employee = Employee.builder()
                .user(user)
                .business(business)
                .position(request.getPosition())
                .isActive(request.isActive())
                .build();

        employee.onCreate();

        return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Verify business ownership
        verifyBusinessAndOwnership(employee.getBusiness().getId());

        // Update employee details
        employee.setPosition(request.getPosition());
        employee.setActive(request.isActive());

        return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Verify business ownership
        verifyBusinessAndOwnership(employee.getBusiness().getId());

        // Reset user role to USER
        User user = employee.getUser();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("User role not found"));
        user.setRole(userRole);
        userRepository.save(user);

        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Check authorization
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isBusinessOwner = employee.getBusiness().getOwner().getId().equals(currentUser.getId());
        boolean isSameEmployee = employee.getUser().getId().equals(currentUser.getId());

        if (!isBusinessOwner && !isSameEmployee) {
            throw new UnauthorizedAccessException("You don't have permission to view this employee's details");
        }

        return employeeMapper.toEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getEmployeesByBusiness(Long businessId) {
        // Verify business ownership
        verifyBusinessAndOwnership(businessId);

        return employeeRepository.findByBusinessId(businessId).stream()
                .map(employeeMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponse> getActiveEmployeesByBusiness(Long businessId) {
        // Verify business ownership
        verifyBusinessAndOwnership(businessId);

        return employeeRepository.findByBusinessIdAndActive(businessId, true).stream()
                .map(employeeMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponse> searchEmployeesByPosition(Long businessId, String position) {
        // Verify business ownership
        verifyBusinessAndOwnership(businessId);

        return employeeRepository.findByBusinessIdAndPosition(businessId, position).stream()
                .map(employeeMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUserEmployeeOfBusiness(Long businessId, Long userId) {
        return employeeRepository.existsByBusinessIdAndUserId(businessId, userId);
    }

    private Business verifyBusinessAndOwnership(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!business.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedAccessException("You don't have permission to manage employees for this business");
        }

        return business;
    }
}