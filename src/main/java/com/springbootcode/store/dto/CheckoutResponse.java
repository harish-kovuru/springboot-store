package com.springbootcode.store.dto;

import lombok.Data;

@Data
public class CheckoutResponse {
    private Long orderId;
    private String checkoutUrl;

    public CheckoutResponse(Long orderId, String url) {
        this.orderId = orderId;
        this.checkoutUrl = url;
    }

}
