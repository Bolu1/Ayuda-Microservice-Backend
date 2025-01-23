package com.ayuda.paymentservice.service;

import com.ayuda.paymentservice.dto.StripeCreatePaymentIntentDto;

public interface IPaymentService {
    /**
     *
     * @param StripeCreatePaymentIntentDto
     * @return
     */
    String createStripePaymentIntent(StripeCreatePaymentIntentDto createPaymentIntentDto);

    /**
     *
     * @param payload
     * @param signature
     */
    void processStripeWebhook(String payload, String signature);
}
