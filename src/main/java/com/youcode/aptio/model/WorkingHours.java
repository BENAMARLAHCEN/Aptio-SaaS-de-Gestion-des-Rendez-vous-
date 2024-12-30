package com.youcode.aptio.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Entity
@Table(name = "working_hours")
@Data
@Builder
public class WorkingHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private DayOfWeek day;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id")
    private Business business;

    public WorkingHours() {
    }

    public WorkingHours(Long id, LocalDateTime startTime, LocalDateTime endTime, DayOfWeek day, Business business) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.business = business;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public static class Builder {
        private Long id;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private DayOfWeek day;
        private Business business;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder day(DayOfWeek day) {
            this.day = day;
            return this;
        }

        public Builder business(Business business) {
            this.business = business;
            return this;
        }

        public WorkingHours build() {
            return new WorkingHours(id, startTime, endTime, day, business);
        }
    }
}
