package com.springbootcode.store.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull(message = "cant be null")
    @Min(value = 1 , message = "cant be less than or equal to one")
    @Max(value = 100)
    private Integer quantity;
}
