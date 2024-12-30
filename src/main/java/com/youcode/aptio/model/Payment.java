package com.youcode.aptio.model;

import com.youcode.aptio.model.enums.PaymentMethod;
import com.youcode.aptio.model.enums.PaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private double amount;
    private PaymentStatus status;
    private PaymentMethod method;
    private String transactionId;
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;

    public Payment() {
    }

    public Payment(Long id, double amount, PaymentStatus status, PaymentMethod method, String transactionId, LocalDateTime createdAt, Appointment appointment) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.transactionId = transactionId;
        this.createdAt = createdAt;
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private double amount;
        private PaymentStatus status;
        private PaymentMethod method;
        private String transactionId;
        private LocalDateTime createdAt;
        private Appointment appointment;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Builder method(PaymentMethod method) {
            this.method = method;
            return this;
        }

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder appointment(Appointment appointment) {
            this.appointment = appointment;
            return this;
        }

        public Payment build() {
            return new Payment(id, amount, status, method, transactionId, createdAt, appointment);
        }
    }
}