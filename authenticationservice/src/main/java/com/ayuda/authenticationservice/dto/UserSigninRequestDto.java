package com.ayuda.authenticationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(
        name = "UserSigninRequestDto",
        description = "Schema for signin"
)
@AllArgsConstructor
@NoArgsConstructor
public class UserSigninRequestDto {

    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Invalid email format")
    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @NotEmpty(message = "Password cannot be null or empty")
    @Schema(description = "User password", example = "password1234")
    private String password;
}
