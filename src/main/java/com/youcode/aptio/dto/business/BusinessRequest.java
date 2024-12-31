package com.youcode.aptio.dto.business;

import com.youcode.aptio.validation.IsValidTimezone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessRequest {
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotEmpty(message = "Description must not be empty")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @NotEmpty(message = "Address must not be empty")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    private String address;

    @NotEmpty(message = "Phone number must not be empty")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phone;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Timezone must not be empty")
    @IsValidTimezone(message = "Invalid timezone provided")
    private String timezone;

    @NotEmpty(message = "Plan must not be empty")
    private String plan;
}