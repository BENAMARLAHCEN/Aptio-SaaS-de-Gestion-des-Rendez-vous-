package com.youcode.aptio.model;

import com.youcode.aptio.model.enums.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "businesses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Default
    private List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    @Default
    private List<WorkingHours> workingHours = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Default
    private List<Employee> employees = new ArrayList<>();

    @Column(name = "created_at")
    @Default
    private LocalDateTime createdAt = LocalDateTime.now();
}