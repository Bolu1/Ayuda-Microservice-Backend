package com.ayuda.authenticationservice.mapper;

import com.ayuda.authenticationservice.dto.SanitizedUserDto;
import com.ayuda.authenticationservice.dto.UserDto;

public class UserDtoMapper {
    public static SanitizedUserDto mapToSanitizedDto(UserDto userDto) {
        return new SanitizedUserDto(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPhoneNumber(),
                userDto.getProfileImageUrl(),
                userDto.isEmailVerified(),
                userDto.isPhoneVerified(),
                userDto.getStatus()
        );
    }

}
