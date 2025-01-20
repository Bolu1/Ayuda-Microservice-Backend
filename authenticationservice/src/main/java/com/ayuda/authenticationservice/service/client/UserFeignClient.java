package com.ayuda.authenticationservice.service.client;

import com.ayuda.authenticationservice.dto.CreateUserAccountDto;
import com.ayuda.authenticationservice.dto.ResponseDto;
import com.ayuda.authenticationservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("userservice")
public interface UserFeignClient {

    @PostMapping("/api/v1/user")
    public ResponseEntity<ResponseDto<UserDto>> createUserAccount (@RequestBody CreateUserAccountDto createUserAccountDto);

    @GetMapping("/api/v1/user/fetch/email/{email}")
    public ResponseEntity<ResponseDto<UserDto>> fetchUserByEmail(@PathVariable("email") String email);

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<ResponseDto<UserDto>> fetchUserById(@PathVariable("id") String userId);

}
