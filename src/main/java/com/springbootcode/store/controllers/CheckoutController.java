package com.springbootcode.store.controllers;

import com.springbootcode.store.dto.CheckoutRequest;
import com.springbootcode.store.dto.CheckoutResponse;
import com.springbootcode.store.dto.ErrorDto;
import com.springbootcode.store.exceptions.CartEmptyException;
import com.springbootcode.store.exceptions.CartNotFoundException;
import com.springbootcode.store.exceptions.PaymentException;
import com.springbootcode.store.repositories.OrderRepository;
import com.springbootcode.store.services.CheckOutService;
import com.springbootcode.store.services.WebhookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckOutService checkOutService;
    private final OrderRepository orderRepository;


    @PostMapping
    public CheckoutResponse checkout(@RequestBody @Valid CheckoutRequest request) {
        return checkOutService.checkOut(request);
    }
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creting a checkout session"));

    }

    @PostMapping("/webhook")
    public void handleWebHook(
            @RequestHeader Map<String,String> headers,
            @RequestBody String payload

    ) {
        checkOutService.handleWebhookEvent(new WebhookRequest(headers,payload));
    }

    @ExceptionHandler({ CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }


}
