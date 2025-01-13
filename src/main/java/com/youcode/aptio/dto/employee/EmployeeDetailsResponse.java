package com.youcode.aptio.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsResponse {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private boolean isActive;
    private Long businessId;
    private String businessName;
    private LocalDateTime createdAt;
    private List<String> workingDays;
    private String workingHours;
}