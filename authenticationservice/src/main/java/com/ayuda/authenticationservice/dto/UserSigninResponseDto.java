package com.ayuda.authenticationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(
        name = "UserSigninResponseDto",
        description = "Schema for signin response"
)
@AllArgsConstructor
@NoArgsConstructor
public class UserSigninResponseDto {
    private SanitizedUserDto userDetails;

    private String authenticationToken;
}
