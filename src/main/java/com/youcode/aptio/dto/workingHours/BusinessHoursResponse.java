package com.youcode.aptio.dto.workingHours;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessHoursResponse {
    private Long businessId;
    private String businessName;
    private Map<String, List<WorkingHoursResponse>> weeklySchedule;
    private boolean isOpen24Hours;
    private List<String> closedDays;
}