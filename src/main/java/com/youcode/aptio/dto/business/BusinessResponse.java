package com.youcode.aptio.dto.business;

import com.youcode.aptio.dto.workingHours.WorkingHoursResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String timezone;
    private String plan;
    private Long ownerId;
    private String ownerName;
    private LocalDateTime createdAt;
    private List<WorkingHoursResponse> workingHours;
}
