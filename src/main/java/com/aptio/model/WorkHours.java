package com.aptio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "work_hours")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(nullable = false)
    private int dayOfWeek; // 0 = Sunday, 1 = Monday, etc.

    @Column(nullable = false)
    private boolean isWorking;

    private LocalTime startTime;

    private LocalTime endTime;

    @OneToMany(mappedBy = "workHours", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TimeSlot> breaks = new ArrayList<>();

    public void addBreak(TimeSlot breakSlot) {
        breaks.add(breakSlot);
        breakSlot.setWorkHours(this);
    }

    public void removeBreak(TimeSlot breakSlot) {
        breaks.remove(breakSlot);
        breakSlot.setWorkHours(null);
    }
}