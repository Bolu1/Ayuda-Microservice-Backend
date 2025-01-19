package com.ayuda.user.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Data
@Schema(name = "UpdateUserAccountDto", description = "Schema for updating user account details")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class UpdateUserAccountDto {
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @Schema(description = "User last name", example = "Doe")
    private String lastName;

    @Schema(description = "User phone number", example = "+1234567890")
    private String phoneNumber;
}