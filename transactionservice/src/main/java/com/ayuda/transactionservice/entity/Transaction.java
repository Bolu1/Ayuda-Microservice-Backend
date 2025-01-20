package com.ayuda.transactionservice.entity;


import com.ayuda.transactionservice.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "reference", nullable = false, unique = true)
    private String reference; // Unique transaction reference

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType; // WALLET_FUND, CARD_PAYMENT, TOKEN_PURCHASE, P2P_TRANSFER, REVERSAL

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency; // USD, SOL, BTC

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status", nullable = false)
    private TransactionStatus status; // PENDING, SUCCESS, FAILED

    @Enumerated(EnumType.STRING)
    @Column(name = "ledger_entry_type", nullable = false)
    private TransactionLedgerEntryType ledgerEntryType; // DEBIT or CREDIT (For double-entry ledger)

    @Column(name = "user_id", nullable = false)
    private String userId; // User who initiated the transaction

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private TransactionSource source; // WALLET, CARD, STRIPE, CRYPTO

    @Enumerated(EnumType.STRING)
    @Column(name = "destination", nullable = false)
    private TransactionDestination destination; // WALLET, CARD, BANK_ACCOUNT, MERCHANT

    @Column(name = "wallet_id")
    private String walletId; // Wallet ID for wallet transactions

    @Column(name = "recipient_wallet_id")
    private String recipientWalletId; // Wallet that received funds (For P2P transfers)

    @Column(name = "card_id")
    private String cardId; // Card ID (Nullable, only for card transactions)

    @Column(name = "transaction_fee")
    private BigDecimal transactionFee; // Optional: Stores transaction fee applied

    @Column(name = "is_reversal", nullable = false)
    private boolean isReversal = false; // True if the transaction is a reversal

    @Column(name = "description")
    private String description;
}
