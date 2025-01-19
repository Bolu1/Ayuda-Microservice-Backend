package com.ayuda.user.service;

import com.ayuda.user.dto.CreateUserAccountDto;
import com.ayuda.user.dto.UpdateUserAccountDto;
import com.ayuda.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IUserService {

    /**
     *
     * @param createUserAccountDto
     * @return
     */
    UserDto createUserAccount (CreateUserAccountDto createUserAccountDto);

    /**
     *
     * @param pageable
     * @return
     */
    Page<UserDto> readUsers (Pageable pageable);

    /**
     *
     * @param userId
     * @return
     */
    UserDto readUserById (String userid);

    /**
     *
     * @param userId
     * @param updateUserAccountDto
     * @param imageUrl
     * @return
     */
    UserDto updateUserById (String userId, UpdateUserAccountDto updateUserAccountDto, String imageUrl);

    /**
     *
     * @param userId
     * @return
     */
    UserDto verifyEmail(String userId);

    /**
     *
     * @param userId
     */
    void deleteUser(String userId);
}
