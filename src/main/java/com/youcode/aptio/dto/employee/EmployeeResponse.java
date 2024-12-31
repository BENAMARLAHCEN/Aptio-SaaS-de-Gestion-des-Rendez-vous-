package com.youcode.aptio.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Long id;
    private Long userId;
    private Long businessId;
    private String position;
    private boolean isActive;
    private LocalDateTime createdAt;
}
