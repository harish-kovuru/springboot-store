package com.springbootcode.store.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemsDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
