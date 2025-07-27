package com.springbootcode.store.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String status;
    private LocalDateTime createAt;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;

}
