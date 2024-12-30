package com.youcode.aptio.model;

import com.youcode.aptio.model.enums.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "businesses")
@Data
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    private String address;
    private String phone;
    private String email;
    private String timezone;

    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<WorkingHours> workingHours = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    private LocalDateTime createdAt;

    public Business() {
    }

    public Business(Long id, String name, String description, String address, String phone, String email, String timezone, SubscriptionPlan plan, List<Service> services, List<WorkingHours> workingHours, List<Employee> employees, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.timezone = timezone;
        this.plan = plan;
        this.services = services;
        this.workingHours = workingHours;
        this.employees = employees;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private String address;
        private String phone;
        private String email;
        private String timezone;
        private SubscriptionPlan plan;
        private List<Service> services = new ArrayList<>();
        private List<WorkingHours> workingHours = new ArrayList<>();
        private List<Employee> employees = new ArrayList<>();
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder timezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder plan(SubscriptionPlan plan) {
            this.plan = plan;
            return this;
        }

        public Builder services(List<Service> services) {
            this.services = services;
            return this;
        }

        public Builder workingHours(List<WorkingHours> workingHours) {
            this.workingHours = workingHours;
            return this;
        }

        public Builder employees(List<Employee> employees) {
            this.employees = employees;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Business build() {
            return new Business(id, name, description, address, phone, email, timezone, plan, services, workingHours, employees, createdAt);
        }
    }
}