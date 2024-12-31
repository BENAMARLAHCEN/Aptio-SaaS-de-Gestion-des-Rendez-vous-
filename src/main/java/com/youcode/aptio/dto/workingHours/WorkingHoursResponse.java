package com.youcode.aptio.dto.workingHours;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkingHoursResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String day;
    private Long businessId;
}
