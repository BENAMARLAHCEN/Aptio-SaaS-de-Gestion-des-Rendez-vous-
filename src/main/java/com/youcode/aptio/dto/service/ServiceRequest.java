package com.youcode.aptio.dto.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    private String name;
    private String description;
    private int duration; // Duration in minutes
    private double price;
    @Builder.Default
    private boolean isActive = true;
    private Long businessId;
}
