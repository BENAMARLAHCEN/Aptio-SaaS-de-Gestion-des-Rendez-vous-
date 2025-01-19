package com.youcode.aptio.dto.business;

import com.youcode.aptio.validation.IsValidTimezone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessUpdateRequest {
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    private String address;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phone;

    @Email(message = "Invalid email address")
    private String email;

    @IsValidTimezone(message = "Invalid timezone provided")
    private String timezone;

    private String plan;
}