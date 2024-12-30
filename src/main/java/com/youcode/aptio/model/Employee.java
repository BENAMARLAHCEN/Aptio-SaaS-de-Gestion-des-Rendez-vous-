package com.youcode.aptio.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String position;
    boolean isActive;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    public Employee() {
    }

    public Employee(Long id, String position, boolean isActive, User user, Business business) {
        this.id = id;
        this.position = position;
        this.isActive = isActive;
        this.user = user;
        this.business = business;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String position;
        private boolean isActive;
        private User user;
        private Business business;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder position(String position) {
            this.position = position;
            return this;
        }

        public Builder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder business(Business business) {
            this.business = business;
            return this;
        }

        public Employee build() {
            return new Employee(id, position, isActive, user, business);
        }
    }
}
