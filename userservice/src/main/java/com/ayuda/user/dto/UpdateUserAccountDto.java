package com.ayuda.user.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Data
@Schema(name = "UpdateUserAccountDto", description = "Schema for updating user account details")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class UpdateUserAccountDto {
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @NotEmpty(message = "Password cannot be null or empty")
    @Schema(description = "User password", example = "password1234")
    private String password;

    @Schema(description = "User last name", example = "Doe")
    private String lastName;

    @Schema(description = "User phone number", example = "+1234567890")
    private String phoneNumber;
}