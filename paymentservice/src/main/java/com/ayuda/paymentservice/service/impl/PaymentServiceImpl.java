package com.ayuda.paymentservice.service.impl;

import com.ayuda.paymentservice.config.AppSettings;
import com.ayuda.paymentservice.dto.StripeCreatePaymentIntentDto;
import com.ayuda.paymentservice.dto.StripeCreatePaymentIntentMetadataDto;
import com.ayuda.paymentservice.enums.TransactionType;
import com.ayuda.paymentservice.service.IPaymentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final AppSettings appSettings;

    /**
     * @param StripeCreatePaymentIntentDto
     * @return
     */
    @Override
    public String createStripePaymentIntent(StripeCreatePaymentIntentDto createPaymentIntentDto) {
        try {
            Stripe.apiKey = appSettings.getStripeSecretKey();

            // Create a Checkout Session
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://frontend.com/success")
                    .setCancelUrl("https://frontend.com/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(createPaymentIntentDto.getCurrency())
                                                    .setUnitAmount(createPaymentIntentDto.getAmount().multiply(BigDecimal.valueOf(100)).longValue()) // Amount in cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Wallet Funding")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .putMetadata("userId", createPaymentIntentDto.getUserId()) // Store metadata
                    .putMetadata("walletId", createPaymentIntentDto.getWalletId())
                    .build();

            Session session = Session.create(params);

            return session.getUrl();

        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe Checkout Session: " + e.getMessage());
        }
    }

    /**
     * @param payload
     * @param signature
     */
    @Override
    public void processStripeWebhook(String payload, String signature) {
        try {
            // ✅ Verify the webhook signature
            Event event = Webhook.constructEvent(payload, signature, appSettings.getStripeWebhookSecret());
            System.out.println("📩 Received Stripe Webhook Event: " + event.getType());

            // ✅ Log the raw JSON in case of deserialization failure
            System.out.println("📜 Raw Event JSON: " + event.toJson());

            if ("checkout.session.completed".equals(event.getType())) {
                // ✅ Manually extract JSON data to prevent deserialization issues
                String eventJson = event.toJson();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(eventJson);

                // ✅ Extract the `data.object` JSON node
                JsonNode sessionNode = rootNode.path("data").path("object");

                if (sessionNode.isMissingNode()) {
                    System.out.println("❌ Failed to extract checkout session from event.");
                    return;
                }

                // ✅ Manually extract metadata
                String userId = sessionNode.path("metadata").path("userId").asText("UNKNOWN_USER");
                String walletId = sessionNode.path("metadata").path("walletId").asText("UNKNOWN_WALLET");
                BigDecimal amount = BigDecimal.valueOf(sessionNode.path("amount_total").asLong()).divide(BigDecimal.valueOf(100)); // Convert cents to dollars

                // ✅ Ensure payment was successful
                String paymentStatus = sessionNode.path("payment_status").asText("unpaid");
                if ("paid".equals(paymentStatus)) {
                    System.out.println("✔ Payment Successful!");
                    System.out.println("🔹 User ID: " + userId);
                    System.out.println("🔹 Wallet ID: " + walletId);
                    System.out.println("💰 Amount Paid: $" + amount);

                    // 🚀 Call Transaction Service to update status
                /*
                transactionService.updateTransactionStatus(sessionNode.path("id").asText(), TransactionStatus.SUCCESS);
                */

                    // 🚀 Call Wallet Service to update balance
                /*
                UpdateWalletBalanceDto walletBalanceDto = new UpdateWalletBalanceDto(
                    walletId,
                    amount,
                    true
                );
                walletService.updateWalletBalance(walletBalanceDto);
                */
                } else {
                    System.out.println("❌ Payment was not successful.");
                }
            }
        } catch (Exception e) {
            System.out.println("🚨 Webhook verification or JSON parsing failed: " + e.getMessage());
            throw new RuntimeException("Webhook processing failed: " + e.getMessage());
        }
    }
}