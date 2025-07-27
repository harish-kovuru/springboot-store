package com.springbootcode.store.services;

import com.springbootcode.store.dto.CheckoutRequest;
import com.springbootcode.store.dto.CheckoutResponse;
import com.springbootcode.store.entities.Order;
import com.springbootcode.store.exceptions.CartEmptyException;
import com.springbootcode.store.exceptions.CartNotFoundException;
import com.springbootcode.store.exceptions.PaymentException;
import com.springbootcode.store.repositories.CartRepository;
import com.springbootcode.store.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckOutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkOut(CheckoutRequest request)  {
        var cart = cartRepository.findById(request.getCartId()).orElseThrow();
        if(cart == null) {
           throw new CartNotFoundException();

        }
        if(cart.isEmpty()) {
           throw new CartEmptyException();
        }

        var order = Order.fromCart(cart,authService.getCurrentUser());

        orderRepository.save(order);
        try{

            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());

        } catch (PaymentException e) {
            orderRepository.delete(order);
            throw e;
        }

    }

    public void  handleWebhookEvent(WebhookRequest request)  {
         paymentGateway
                 .parseWebhokRequest(request)
                 .ifPresent( paymentResult -> {
                     var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow(null);
                     order.setStatus(paymentResult.getPaymentStatus());
                     orderRepository.save(order);
                 });
    }
}
