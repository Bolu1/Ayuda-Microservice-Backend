package com.ayuda.user.controller;

import com.ayuda.user.constants.UsersConstants;
import com.ayuda.user.dto.*;
import com.ayuda.user.mapper.UserMapper;
import com.ayuda.user.service.IUserService;
import com.ayuda.user.utils.services.CloudinaryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Adetifa Bolu
 */

@Tag(
        name = "CRUD REST APIs for user service in Ayuda",
        description = "CRUD REST APIs for user service to CREATE, UPDATE, FETCH AND DELETE user details"
)
@RestController
@RequestMapping(path="/api/v1/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private IUserService iUserService;
    private CloudinaryService cloudinaryService;

    public UserController (
            IUserService iUserService,
            CloudinaryService cloudinaryService
    ) {
        this.cloudinaryService = cloudinaryService;
        this.iUserService = iUserService;
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
    @PostMapping("")
    public ResponseEntity<ResponseDto<UserDto>> createUserAccount (@Valid @RequestBody CreateUserAccountDto createUserAccountDto) {
        UserDto createdUser = iUserService.createUserAccount(createUserAccountDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(UsersConstants.STATUS_201, UsersConstants.MESSAGE_201, Optional.of(createdUser)));
    }


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Users fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("")
    public ResponseEntity<ResponseDto<Page<UserDto>>> fetchUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> users = iUserService.readUsers(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(UsersConstants.STATUS_200, UsersConstants.MESSAGE_200, Optional.of(users)));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserDto>> fetchUserById(@PathVariable("id") String userId) {
        UserDto userFromDB = iUserService.readUserById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(UsersConstants.STATUS_200, UsersConstants.MESSAGE_200, Optional.of(userFromDB)));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PatchMapping(value="/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<UserDto>> updateUserAccount(
            @PathVariable("id") String userId,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = cloudinaryService.uploadFile(file);
        }

        UpdateUserAccountDto updateUserAccountDto = UpdateUserAccountDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .build();

        UserDto updatedUser = iUserService.updateUserById(userId, updateUserAccountDto, imageUrl);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(UsersConstants.STATUS_200, UsersConstants.MESSAGE_200, Optional.of(updatedUser)));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User email verified successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PatchMapping("/verify-email/{id}")
    public ResponseEntity<ResponseDto<UserDto>> verifyUserEmail(@PathVariable("id") String userId) {
        UserDto updatedUser = iUserService.verifyEmail(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(UsersConstants.STATUS_200, UsersConstants.MESSAGE_200, Optional.of(updatedUser)));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteUserById(@PathVariable("id") String userId) {
        iUserService.deleteUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(UsersConstants.STATUS_200, UsersConstants.MESSAGE_200, Optional.empty()));
    }


}
