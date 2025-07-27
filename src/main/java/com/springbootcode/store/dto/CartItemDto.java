package com.springbootcode.store.dto;

import lombok.Data;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private ItemsDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
