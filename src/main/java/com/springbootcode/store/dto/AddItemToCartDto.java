package com.springbootcode.store.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartDto {
    @NotNull
    private Long productId;
}
