package com.aptio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private String id;

    private String userId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    private String email;

    private String phone;

    @NotBlank(message = "Position is required")
    private String position;

    private Set<String> specialties;

    private String color;

    private String avatar;

    @NotNull(message = "Active status is required")
    private Boolean isActive;

    private List<WorkHoursDTO> workHours;
}


