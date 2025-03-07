package com.aptio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategoryDTO {
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private boolean active;

    private int servicesCount;
}