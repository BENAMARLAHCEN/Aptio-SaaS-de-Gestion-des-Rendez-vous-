package com.aptio.controller;

import com.aptio.dto.AppointmentDTO;
import com.aptio.dto.DashboardStatsDTO;
import com.aptio.service.AppointmentService;
import com.aptio.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final AppointmentService appointmentService;

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    @GetMapping("/appointments/recent")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<List<AppointmentDTO>> getRecentAppointments(
            @RequestParam(defaultValue = "5") int limit) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDate(LocalDate.now());

        int resultSize = Math.min(limit, appointments.size());
        return ResponseEntity.ok(appointments.subList(0, resultSize));
    }

    @GetMapping("/appointments/upcoming")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<List<AppointmentDTO>> getUpcomingAppointments() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        return ResponseEntity.ok(appointmentService.getAppointmentsByDateRange(today, nextWeek));
    }
}