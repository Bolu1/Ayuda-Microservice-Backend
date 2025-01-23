package com.ayuda.authenticationservice.controller;

import com.ayuda.authenticationservice.constants.AuthenticationConstants;
import com.ayuda.authenticationservice.dto.*;
import com.ayuda.authenticationservice.mapper.UserDtoMapper;
import com.ayuda.authenticationservice.service.client.UserFeignClient;
import com.ayuda.authenticationservice.utils.security.BcryptService;
import com.ayuda.authenticationservice.utils.security.JwtService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * @author Adetifa Bolu
 */

@Tag(
        name = "CRUD REST APIs for authentication service in Ayuda",
        description = "CRUD REST APIs for authentication service"
)
@RestController
@RequestMapping(path="/api/v1/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final JwtService jwtService;
    private final BcryptService bcryptService;
    private UserFeignClient userFeignClient;

    public AuthenticationController(UserFeignClient userFeignClient, JwtService jwtService, BcryptService bcryptService) {
        this.jwtService = jwtService;
        this.bcryptService = bcryptService;
        this.userFeignClient = userFeignClient;
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<UserDto>> userSignup (@Valid @RequestBody CreateUserAccountDto createUserAccountDto) {
        createUserAccountDto.setPassword(bcryptService.hashPassword(createUserAccountDto.getPassword()));
        return userFeignClient.createUserAccount(createUserAccountDto);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<UserSigninResponseDto>> userSignin (@Valid @RequestBody UserSigninRequestDto userSigninRequestDto) {
        ResponseEntity<ResponseDto<UserDto>> fetchUserResponse = userFeignClient.fetchUserByEmail(userSigninRequestDto.getEmail());
        UserDto userFromDb = fetchUserResponse.getBody().getData().orElseThrow(() -> new BadCredentialsException("Invalid login details"));

        if(!bcryptService.verifyPassword(userSigninRequestDto.getPassword(), userFromDb.getPassword())) {
            new BadCredentialsException(("Invalid login details"));
        }

        String authenticationToken = jwtService.generateToken(userFromDb.getId());
        UserSigninResponseDto userSigninResponseDto = new UserSigninResponseDto(UserDtoMapper.mapToSanitizedDto(userFromDb), authenticationToken);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(AuthenticationConstants.STATUS_200, AuthenticationConstants.MESSAGE_200, Optional.of(userSigninResponseDto)));
    }

}
