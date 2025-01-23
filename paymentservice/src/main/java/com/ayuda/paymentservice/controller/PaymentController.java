package com.ayuda.paymentservice.controller;

import com.ayuda.paymentservice.constants.PaymentConstants;
import com.ayuda.paymentservice.dto.ResponseDto;
import com.ayuda.paymentservice.dto.StripeCreatePaymentIntentDto;
import com.ayuda.paymentservice.service.IPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payment")
@Tag(name = "Payment Service", description = "Handles payment processing via Stripe")
@AllArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    @Operation(
            summary = "Create a Stripe Payment Intent",
            description = "Initiates a payment process with Stripe and returns a client secret to confirm the payment."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment Intent created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Stripe payment creation failed")
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createPaymentIntent(@RequestBody StripeCreatePaymentIntentDto dto) {
        String checkoutUrl = paymentService.createStripePaymentIntent(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(PaymentConstants.STATUS_200, PaymentConstants.MESSAGE_200, Optional.of(checkoutUrl)));
    }

    @Operation(
            summary = "Stripe Webhook Handler",
            description = "Handles Stripe webhook events, verifying payment status and updating transaction records."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Webhook processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid webhook payload or signature"),
            @ApiResponse(responseCode = "500", description = "Webhook verification failed")
    })
    @PostMapping("/webhook")
    public ResponseEntity<ResponseDto> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature) {
        paymentService.processStripeWebhook(payload, signature);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(PaymentConstants.STATUS_200, PaymentConstants.MESSAGE_200, null));
    }
}
