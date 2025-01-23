package com.ayuda.paymentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "StripeCreatePaymentIntentDto", description = "DTO for creating a payment intent with Stripe")
public class StripeCreatePaymentIntentDto {

    @Schema(description = "The ID of the user initiating the payment", example = "user_12345")
    private String userId;

    @Schema(description = "The ID of the wallet being funded", example = "wallet_67890")
    private String walletId;

    @Schema(description = "The amount to be charged (in decimal format)", example = "1.50")
    private BigDecimal amount;

    @Schema(description = "The currency for the transaction", example = "USD")
    private String currency;
}