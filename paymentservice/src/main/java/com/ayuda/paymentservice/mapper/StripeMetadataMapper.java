package com.ayuda.paymentservice.mapper;

import com.ayuda.paymentservice.dto.StripeCreatePaymentIntentMetadataDto;
import com.ayuda.paymentservice.enums.TransactionType;

import java.util.Map;

public class StripeMetadataMapper {

    /**
     * Converts StripeCreatePaymentIntentMetadataDto to a Map<String, String> for Stripe metadata
     */
    public static Map<String, String> toMap(StripeCreatePaymentIntentMetadataDto metadata) {
        return Map.of(
                "userId", metadata.getUserId(),
                "walletId", metadata.getWalletId(),
                "transactionType", metadata.getTransactionType().name()
        );
    }

    /**
     * Creates a StripeCreatePaymentIntentMetadataDto from a Stripe metadata Map<String, String>
     */
    public static StripeCreatePaymentIntentMetadataDto fromMap(Map<String, String> metadata) {
        if (metadata == null) {
            throw new IllegalArgumentException("Metadata cannot be null");
        }

        return new StripeCreatePaymentIntentMetadataDto(
                metadata.get("userId"),
                metadata.get("walletId"),
                TransactionType.valueOf(metadata.get("transactionType"))
        );
    }
}
