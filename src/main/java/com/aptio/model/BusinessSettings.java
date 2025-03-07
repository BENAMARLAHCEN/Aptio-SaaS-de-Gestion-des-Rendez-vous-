package com.aptio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "business_settings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BusinessSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime businessHoursStart;

    @Column(nullable = false)
    private LocalTime businessHoursEnd;

    @Column(nullable = false, length = 7)
    private String daysOpen; // 7-length string of 0/1, e.g., "0111110" means closed on Sun & Sat

    @Column(nullable = false)
    private int defaultAppointmentDuration; // in minutes

    @Column(nullable = false)
    private int timeSlotInterval; // in minutes

    @Column(nullable = false)
    private boolean allowOverlappingAppointments;

    @Column(nullable = false)
    private int bufferTimeBetweenAppointments; // in minutes

    @Column(nullable = false)
    private String businessName;

    private String address;

    private String phone;

    private String email;

    private String website;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public boolean[] getDaysOpenArray() {
        boolean[] result = new boolean[7];
        for (int i = 0; i < 7 && i < daysOpen.length(); i++) {
            result[i] = daysOpen.charAt(i) == '1';
        }
        return result;
    }

    public void setDaysOpenArray(boolean[] days) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            builder.append(i < days.length && days[i] ? '1' : '0');
        }
        this.daysOpen = builder.toString();
    }
}