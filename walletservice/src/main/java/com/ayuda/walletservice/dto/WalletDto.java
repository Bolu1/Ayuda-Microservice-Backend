package com.ayuda.walletservice.dto;

import com.ayuda.walletservice.enums.WalletStatus;
import com.ayuda.walletservice.enums.WalletType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "WalletDto",
        description = "Schema for returning wallet details"
)
public class WalletDto {

    @Schema(description = "Wallet ID", example = "abc123-def456")
    private String id;

    @Schema(description = "User ID associated with the wallet", example = "123e4567-e89b-12d3-a456-426614174000")
    private String userId;

    @Schema(description = "Custom wallet name", example = "Main Wallet")
    private String walletName;

    @Schema(description = "Type of wallet (FIAT or CRYPTO)", example = "FIAT")
    private WalletType walletType;

    @Schema(description = "Currency of the wallet", example = "USD")
    private String currency;

    @Schema(description = "Current balance of the wallet", example = "1000.50")
    private BigDecimal balance;

    @Schema(description = "Status of the wallet", example = "ACTIVE")
    private WalletStatus status;

    @Schema(description = "Indicates if this is the default wallet", example = "false")
    private boolean isDefault;

    @Schema(description = "Optional description of the wallet", example = "Savings account for daily expenses")
    private String description;

    @Schema(description = "Unique account number for P2P transactions", example = "123456789012")
    private String accountNumber;
}
