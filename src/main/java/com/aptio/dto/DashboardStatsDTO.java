package com.aptio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private int totalAppointments;
    private int newCustomers;
    private double utilizationRate;
    private double averageFeedback;
    private List<AppointmentDTO> recentAppointments;
}