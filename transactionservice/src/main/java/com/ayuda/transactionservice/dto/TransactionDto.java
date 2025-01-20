package com.ayuda.transactionservice.dto;

import com.ayuda.transactionservice.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TransactionDTO", description = "Schema for transaction details")
public class TransactionDto {

    @Schema(description = "Unique transaction ID", example = "txn-12345")
    private String id;

    @Schema(description = "Unique transaction reference", example = "REF-987654")
    private String reference;

    @Schema(description = "Type of transaction", example = "P2P_TRANSFER")
    private TransactionType transactionType;

    @Schema(description = "Transaction amount", example = "100.50")
    private BigDecimal amount;

    @Schema(description = "Currency of the transaction", example = "USD")
    private String currency;

    @Schema(description = "Transaction status", example = "SUCCESS")
    private TransactionStatus status;

    @Schema(description = "Ledger entry type (DEBIT or CREDIT)", example = "DEBIT")
    private TransactionLedgerEntryType ledgerEntryType;

    @Schema(description = "User ID of the transaction initiator", example = "user-123")
    private String userId;

    @Schema(description = "Transaction source", example = "WALLET")
    private TransactionSource source;

    @Schema(description = "Transaction destination", example = "WALLET")
    private TransactionDestination destination;

    @Schema(description = "Wallet ID (if applicable)", example = "wallet-123")
    private String walletId;

    @Schema(description = "Recipient Wallet ID (if applicable)", example = "wallet-456")
    private String recipientWalletId;

    @Schema(description = "Card ID (if applicable)", example = "card-789")
    private String cardId;

    @Schema(description = "Transaction fee", example = "2.50")
    private BigDecimal transactionFee;

    @Schema(description = "Indicates if the transaction is a reversal", example = "false")
    private boolean isReversal;

    @Schema(description = "Transaction description", example = "P2P transfer to John")
    private String description;

    @Schema(description = "Transaction creation timestamp", example = "2024-01-16T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Transaction update timestamp", example = "2024-01-16T11:00:00")
    private LocalDateTime updatedAt;
}
