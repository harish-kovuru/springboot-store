package com.springbootcode.store.services;

import com.springbootcode.store.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);

    Optional<PaymentResult> parseWebhokRequest(WebhookRequest request);
}
