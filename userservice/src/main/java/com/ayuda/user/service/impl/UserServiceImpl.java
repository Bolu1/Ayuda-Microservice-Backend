package com.ayuda.user.service.impl;

import com.ayuda.user.dto.CreateUserAccountDto;
import com.ayuda.user.dto.UpdateUserAccountDto;
import com.ayuda.user.dto.UserDto;
import com.ayuda.user.entity.Users;
import com.ayuda.user.enums.UserStatus;
import com.ayuda.user.exception.ResourceNotFoundException;
import com.ayuda.user.exception.UserAlreadyExistsException;
import com.ayuda.user.mapper.UserMapper;
import com.ayuda.user.repository.UserRepository;
import com.ayuda.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;


    /**
     * @param createUserAccountDto
     * @return
     */
    @Override
    public UserDto createUserAccount(CreateUserAccountDto createUserAccountDto) {
        Users user = UserMapper.mapFromCreateUserAccountDto(createUserAccountDto, new Users());

        // Check if email is in use
        Optional<Users> emailUserFromDB = userRepository.findByEmail(user.getEmail());
        if (emailUserFromDB.isPresent()) {
            throw new UserAlreadyExistsException("Email in use");
        }

        // Check is phone number is in use
        Optional<Users> phoneNumberUserFromDB = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (phoneNumberUserFromDB.isPresent()) {
            throw new UserAlreadyExistsException("Phone number in use");
        }

        Users savedUser = userRepository.save(user);

        return UserMapper.mapToDto(savedUser, new UserDto());
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<UserDto> readUsers(Pageable pageable) {
        Page<Users> usersPage = userRepository.findAll(pageable);
        return usersPage.map(user -> UserMapper.mapToDto(user, new UserDto()));
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public UserDto readUserById(String userId) {
        Users userFromDB = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return UserMapper.mapToDto(userFromDB, new UserDto());
    }

    /**
     * @param email
     * @return
     */
    @Override
    public UserDto readUserByEmail(String email) {
        Users userFromDB = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return UserMapper.mapToDto(userFromDB, new UserDto());
    }

    /**
     * @param userId
     * @param updateUserAccountDto
     * @param imageUrl
     * @return
     */
    @Override
    public UserDto updateUserById(String userId, UpdateUserAccountDto updateUserAccountDto, String imageUrl) {
        Users userFromDB = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Users user = UserMapper.mapFromUpdateUserAccountDto(updateUserAccountDto, userFromDB);

        if (imageUrl != null) {
            user.setProfileImageUrl(imageUrl);
        }

        user = userRepository.save(user);

        return UserMapper.mapToDto(user, new UserDto());
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public UserDto verifyEmail(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setEmailVerified(true);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);
        return UserMapper.mapToDto(user, new UserDto());
    }

    /**
     * @param userId
     */
    @Override
    public void deleteUser(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);
    }
}
