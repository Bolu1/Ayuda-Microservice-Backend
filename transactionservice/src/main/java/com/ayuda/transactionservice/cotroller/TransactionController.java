package com.ayuda.transactionservice.cotroller;

import com.ayuda.transactionservice.constants.TransactionConstants;
import com.ayuda.transactionservice.dto.CreateTransactionDto;
import com.ayuda.transactionservice.dto.ErrorResponseDto;
import com.ayuda.transactionservice.dto.ResponseDto;
import com.ayuda.transactionservice.dto.TransactionDto;
import com.ayuda.transactionservice.service.ITransactionService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.Optional;

/**
 * @author Adetifa Bolu
 */

@Tag(
        name = "Transaction Service APIs",
        description = "REST APIs for handling Transactions"
)
@RestController
@RequestMapping(path = "/api/v1/transactions", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final ITransactionService transactionService;

    /**
     * Create a new transaction
     */
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("")
    public ResponseEntity<ResponseDto<TransactionDto>> createTransaction(@Valid @RequestBody CreateTransactionDto createTransactionDto) {
        TransactionDto transaction = transactionService.createTransaction(createTransactionDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto<>(TransactionConstants.STATUS_201, TransactionConstants.MESSAGE_201, Optional.of(transaction)));
    }

    /**
     * Fetch a transaction by ID
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transaction fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<TransactionDto>> getTransactionById(@PathVariable("id") String transactionId) {
        TransactionDto transaction = transactionService.getTransactionById(transactionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(TransactionConstants.STATUS_200, TransactionConstants.MESSAGE_200, Optional.of(transaction)));
    }

    /**
     * Fetch all transactions of a user
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transactions fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto<Page<TransactionDto>>> getUserTransactions(
            @PathVariable("userId") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionDto> transactions = transactionService.getUserTransactions(userId, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(TransactionConstants.STATUS_200, TransactionConstants.MESSAGE_200, Optional.of(transactions)));
    }

    /**
     * Fetch all transactions related to a specific wallet
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wallet transactions fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<ResponseDto<Page<TransactionDto>>> getWalletTransactions(
            @PathVariable("walletId") String walletId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionDto> transactions = transactionService.getWalletTransactions(walletId, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(TransactionConstants.STATUS_200, TransactionConstants.MESSAGE_200, Optional.of(transactions)));
    }

    /**
     * Fetch all transactions related to a specific card
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card transactions fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/card/{cardId}")
    public ResponseEntity<ResponseDto<Page<TransactionDto>>> getCardTransactions(
            @PathVariable("cardId") String cardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionDto> transactions = transactionService.getCardTransactions(cardId, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(TransactionConstants.STATUS_200, TransactionConstants.MESSAGE_200, Optional.of(transactions)));
    }

    /**
     * Reverse a transaction
     */
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transaction reversed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid transaction for reversal", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/reverse/{id}")
    public ResponseEntity<ResponseDto<TransactionDto>> reverseTransaction(@PathVariable("id") String transactionId) {
        TransactionDto reversedTransaction = transactionService.reverseTransaction(transactionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(TransactionConstants.STATUS_201, TransactionConstants.MESSAGE_201, Optional.of(reversedTransaction)));
    }
}
