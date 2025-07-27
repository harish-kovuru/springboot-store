package com.springbootcode.store.mappers;

import com.springbootcode.store.dto.CartDto;
import com.springbootcode.store.dto.CartItemDto;
import com.springbootcode.store.entities.Cart;
import com.springbootcode.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CartMapper {

        @Mapping(target = "totalPrice", expression = "java(cart.getTotal())")
      CartDto toCartDto(Cart cart);

    @Mapping(target = "totalPrice",expression ="java(cartItem.getTotal())" )
    CartItemDto toCartItemDto(CartItem cartItem);
}
