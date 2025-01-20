package com.ayuda.transactionservice.dto;

import com.ayuda.transactionservice.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreateTransactionDTO", description = "Schema for creating a transaction")
public class CreateTransactionDto {

    @NotNull(message = "Transaction type cannot be null")
    @Schema(description = "Type of transaction", example = "P2P_TRANSFER")
    private TransactionType transactionType; // WALLET_FUND, CARD_PAYMENT, TOKEN_PURCHASE, P2P_TRANSFER, REVERSAL

    @NotNull(message = "Amount cannot be null")
    @Schema(description = "Transaction amount", example = "100.50")
    private BigDecimal amount;

    @NotNull(message = "Currency cannot be null")
    @Schema(description = "Currency of the transaction", example = "USD")
    private String currency; // USD, SOL, BTC

    @NotNull(message = "Ledger entry type cannot be null")
    @Schema(description = "DEBIT or CREDIT (For double-entry ledger)", example = "DEBIT")
    private TransactionLedgerEntryType ledgerEntryType; // DEBIT or CREDIT

    @NotNull(message = "User ID cannot be null")
    @Schema(description = "ID of the user initiating the transaction", example = "3d5d09b4-158b-436d-8986-c319d1f085ad")
    private String userId; // User who initiated the transaction

    @NotNull(message = "Transaction source cannot be null")
    @Schema(description = "Source of transaction", example = "WALLET")
    private TransactionSource source; // WALLET, CARD, STRIPE, CRYPTO

    @NotNull(message = "Transaction destination cannot be null")
    @Schema(description = "Destination of transaction", example = "WALLET")
    private TransactionDestination destination; // WALLET, CARD, BANK_ACCOUNT, MERCHANT

    @Schema(description = "Wallet ID (For wallet transactions)", example = "wallet-123")
    private String walletId; // Wallet ID for wallet transactions

    @Schema(description = "Recipient Wallet ID (For P2P transfers)", example = "wallet-456")
    private String recipientWalletId; // Wallet that received funds (For P2P transfers)

    @Schema(description = "Card ID (Only for card transactions)", example = "card-789")
    private String cardId; // Card ID (Nullable, only for card transactions)

    @Schema(description = "Transaction fee applied", example = "2.50")
    private BigDecimal transactionFee; // Optional: Stores transaction fee applied

    @Schema(description = "Indicates if the transaction is a reversal", example = "false")
    private boolean isReversal = false; // True if the transaction is a reversal

    @Schema(description = "Description of the transaction", example = "P2P transfer to John")
    private String description;

    @Schema(description = "Transaction status", example = "SUCCESS")
    private TransactionStatus status;
}
