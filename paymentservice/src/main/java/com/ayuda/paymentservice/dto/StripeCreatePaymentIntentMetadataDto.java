package com.ayuda.paymentservice.dto;

import com.ayuda.paymentservice.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeCreatePaymentIntentMetadataDto {
    private String userId;
    private String walletId;
    private TransactionType transactionType;
}
