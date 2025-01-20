package com.ayuda.walletservice.entity;

import com.ayuda.walletservice.enums.WalletStatus;
import com.ayuda.walletservice.enums.WalletType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId; // User who owns this wallet

    @Column(name = "wallet_name", nullable = false)
    private String walletName; // Custom wallet name (e.g., "Main Wallet", "Crypto Wallet")

    @Enumerated(EnumType.STRING)
    @Column(name = "wallet_type", nullable = false)
    private WalletType walletType; // FIAT or CRYPTO

    @Column(name = "currency", nullable = false)
    private String currency; // USD, EUR, SOL, BTC

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO; // Default balance is 0

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WalletStatus status; // ACTIVE, FROZEN, CLOSED

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false; // Default wallet for the user

    @Column(name = "description")
    private String description; // Optional description for the wallet

    @Column(name = "account_number", nullable = false, unique = true, length = 12)
    private String accountNumber; // Unique wallet identifier for P2P transactions
}
