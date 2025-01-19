package com.ayuda.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Schema(
        name = "CreateAccountDto",
        description = "Schema for user account creation"
)
@AllArgsConstructor @NoArgsConstructor
public class CreateUserAccountDto {
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Invalid email format")
    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @NotEmpty(message = "First name cannot be null or empty")
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @NotEmpty(message = "Last name cannot be null or empty")
    @Schema(description = "User last name", example = "Doe")
    private String lastName;

    @NotEmpty(message = "Phone number cannot be null or empty")
    @Schema(description = "User phone number", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Device Token for push notifications", example = "random_device_token")
    private String deviceToken;
}
