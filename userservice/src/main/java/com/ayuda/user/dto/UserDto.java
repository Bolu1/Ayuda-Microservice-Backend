package com.ayuda.user.dto;

import com.ayuda.user.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Schema(
        name = "UserDto",
        description = "Schema to hold user information"
)
@AllArgsConstructor @NoArgsConstructor
public class UserDto {

    @NotEmpty(message = "ID cannot be null or empty")
    @Schema(description = "User ID", example = "3d5d09b4-158b-436d-8986-c319d1f085ad")
    private String id;

    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Invalid email format")
    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @NotEmpty(message = "Password cannot be null or empty")
    @Schema(description = "User password", example = "password1234")
    private String password;

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

    @Schema(description = "Last login timestamp", example = "2024-01-16T10:30:00")
    private LocalDateTime lastLoggedInAt;

    @Schema(description = "Profile image URL", example = "https://example.com/profile.jpg")
    private String profileImageUrl;

    @Schema(description = "Indicates if the email is verified", example = "true")
    private boolean isEmailVerified;

    @Schema(description = "Indicates if the phone number is verified", example = "false")
    private boolean isPhoneVerified;

    @Schema(description = "User account status", example = "ACTIVE")
    private UserStatus status;
}
