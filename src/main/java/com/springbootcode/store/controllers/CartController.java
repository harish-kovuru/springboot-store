package com.springbootcode.store.controllers;

import com.springbootcode.store.dto.AddItemToCartDto;
import com.springbootcode.store.dto.CartDto;
import com.springbootcode.store.dto.CartItemDto;
import com.springbootcode.store.dto.UpdateCartItemRequest;
import com.springbootcode.store.exceptions.CartNotFoundException;
import com.springbootcode.store.exceptions.ProductNotFoundException;
import com.springbootcode.store.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(){
        var cartDto = cartService.createCart();
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addItems(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartDto request
    ){

        var cartDto = cartService.addToCart(cartId,request.getProductId());
        cartDto.setTotalPrice(cartDto.getTotalPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(
            @PathVariable UUID cartId
    ) {
       return cartService.getCart(cartId);

    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCartItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
            ) {
            return cartService.updateCart(cartId, productId, request.getQuantity());

    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId
            ) {
            cartService.removeItem(cartId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> removeCart(
            @PathVariable("cartId") UUID cartId
    ){
            cartService.clearCart(cartId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found"));
    }
}
