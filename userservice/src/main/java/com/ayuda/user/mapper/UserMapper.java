package com.ayuda.user.mapper;

import com.ayuda.user.dto.CreateUserAccountDto;
import com.ayuda.user.dto.SanitizedUserDto;
import com.ayuda.user.dto.UpdateUserAccountDto;
import com.ayuda.user.dto.UserDto;
import com.ayuda.user.entity.Users;
import com.ayuda.user.enums.UserStatus;

public class UserMapper {

    // Convert Users entity to UserDto
    public static UserDto mapToDto(Users user, UserDto userDto) {
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setDeviceToken(user.getDeviceToken());
        userDto.setLastLoggedInAt(user.getLastLoggedInAt());
        userDto.setProfileImageUrl(user.getProfileImageUrl());
        userDto.setEmailVerified(user.isEmailVerified());
        userDto.setPhoneVerified(user.isPhoneVerified());
        userDto.setStatus(user.getStatus());

        return userDto;
    }

    // Convert UserDto to Users entity
    public static Users mapFromDto(UserDto userDto, Users user) {
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setDeviceToken(userDto.getDeviceToken());
        user.setLastLoggedInAt(userDto.getLastLoggedInAt());
        user.setProfileImageUrl(userDto.getProfileImageUrl());
        user.setEmailVerified(userDto.isEmailVerified());
        user.setPhoneVerified(userDto.isPhoneVerified());
        user.setStatus(userDto.getStatus());

        return user;
    }

    public static Users mapFromCreateUserAccountDto(CreateUserAccountDto createAccountDto, Users user) {
        user.setEmail(createAccountDto.getEmail());
        user.setPassword(createAccountDto.getPassword());
        user.setFirstName(createAccountDto.getFirstName());
        user.setLastName(createAccountDto.getLastName());
        user.setPhoneNumber(createAccountDto.getPhoneNumber());
        user.setDeviceToken(createAccountDto.getDeviceToken());

        // Default values
        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setStatus(UserStatus.INACTIVE);

        return user;
    }

    public static Users mapFromUpdateUserAccountDto(UpdateUserAccountDto updateAccountDto, Users user) {
        if (updateAccountDto.getFirstName() != null) {
            user.setFirstName(updateAccountDto.getFirstName());
        }
        if (updateAccountDto.getLastName() != null) {
            user.setLastName(updateAccountDto.getLastName());
        }
        if (updateAccountDto.getPhoneNumber() != null) {
            user.setPhoneNumber(updateAccountDto.getPhoneNumber());
        }
        if (updateAccountDto.getPassword() != null) {
            user.setPassword(updateAccountDto.getPassword());
        }
        return user;
    }

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
