package com.aptio.dto;

import com.aptio.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private Address address;
    private LocalDate birthDate;
    private String gender;
    private List<CustomerNoteDTO> notes;
    private String profileImage;
    private boolean active;
    private int totalVisits;
    private BigDecimal totalSpent;
    private LocalDateTime lastVisit;
    private LocalDateTime registrationDate;
}