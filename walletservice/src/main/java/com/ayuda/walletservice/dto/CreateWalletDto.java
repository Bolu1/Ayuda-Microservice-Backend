package com.ayuda.walletservice.dto;

import com.ayuda.walletservice.enums.WalletType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "CreateWalletDto",
        description = "Schema for wallet creation request"
)
public class CreateWalletDto {

    @NotEmpty(message = "User ID cannot be empty")
    @Schema(description = "User ID associated with the wallet", example = "123e4567-e89b-12d3-a456-426614174000")
    private String userId;

    @NotEmpty(message = "Wallet name cannot be empty")
    @Schema(description = "Custom wallet name", example = "Main Wallet")
    private String walletName;

    @NotNull(message = "Wallet type is required")
    @Schema(description = "Type of wallet (FIAT or CRYPTO)", example = "FIAT")
    private WalletType walletType;

    @NotEmpty(message = "Currency cannot be empty")
    @Schema(description = "Currency of the wallet", example = "USD")
    private String currency;

    @Schema(description = "Set wallet as default", example = "false")
    private boolean isDefault = false;

    @Schema(description = "Optional description of the wallet", example = "Savings account for daily expenses")
    private String description;
}
