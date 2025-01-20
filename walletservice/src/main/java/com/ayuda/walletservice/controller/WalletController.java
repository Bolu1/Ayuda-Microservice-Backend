package com.ayuda.walletservice.controller;

import com.ayuda.walletservice.dto.CreateWalletDto;
import com.ayuda.walletservice.dto.ErrorResponseDto;
import com.ayuda.walletservice.dto.ResponseDto;
import com.ayuda.walletservice.dto.WalletDto;
import com.ayuda.walletservice.enums.WalletStatus;
import com.ayuda.walletservice.service.IWalletService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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

import java.math.BigDecimal;
import java.util.Optional;

@Tag(
        name = "Wallet Management",
        description = "CRUD APIs for wallet management in Ayuda"
)
@RestController
@RequestMapping(path = "/api/v1/wallet", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class WalletController {

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    private final IWalletService walletService;

    public WalletController(IWalletService walletService) {
        this.walletService = walletService;
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Wallet created successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PostMapping("")
    public ResponseEntity<ResponseDto<WalletDto>> createWallet(@Valid @RequestBody CreateWalletDto createWalletDto) {
        WalletDto walletDto = walletService.createWallet(createWalletDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto<>(HttpStatus.CREATED.toString(), "Wallet created successfully", Optional.of(walletDto)));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetched all user wallets"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto<Page<WalletDto>>> getUserWallets(
            @PathVariable("userId") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WalletDto> wallets = walletService.getUserWallets(userId, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(HttpStatus.OK.toString(), "User wallets fetched successfully", Optional.of(wallets)));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Wallet details fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Wallet not found"
            )
    })
    @GetMapping("/{walletId}")
    public ResponseEntity<ResponseDto<WalletDto>> getWalletById(@PathVariable("walletId") String walletId) {
        WalletDto walletDto = walletService.getWalletById(walletId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(HttpStatus.OK.toString(), "Wallet details fetched successfully", Optional.of(walletDto)));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Wallet balance fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Wallet not found"
            )
    })
    @GetMapping("/{walletId}/balance")
    public ResponseEntity<ResponseDto<BigDecimal>> getWalletBalance(@PathVariable("walletId") String walletId) {
        BigDecimal balance = walletService.getWalletBalance(walletId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(HttpStatus.OK.toString(), "Wallet balance fetched successfully", Optional.of(balance)));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Wallet status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Wallet not found"
            )
    })
    @PatchMapping("/{walletId}/status")
    public ResponseEntity<ResponseDto<WalletDto>> changeWalletStatus(
            @PathVariable("walletId") String walletId,
            @RequestParam WalletStatus status) {
        WalletDto updatedWallet = walletService.changeWalletStatus(walletId, status);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(HttpStatus.OK.toString(), "Wallet status updated successfully", Optional.of(updatedWallet)));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Wallet balance updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PatchMapping("/{walletId}/balance")
    public ResponseEntity<ResponseDto<WalletDto>> updateWalletBalance(
            @PathVariable("walletId") String walletId,
            @RequestParam @Positive BigDecimal amount,
            @RequestParam boolean isCredit) {
        WalletDto updatedWallet = walletService.updateWalletBalance(walletId, amount, isCredit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(HttpStatus.OK.toString(), "Wallet balance updated successfully", Optional.of(updatedWallet)));
    }
}
