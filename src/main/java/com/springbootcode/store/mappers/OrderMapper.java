package com.springbootcode.store.mappers;

import com.springbootcode.store.dto.OrderDto;
import com.springbootcode.store.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto  toDto(Order order);
}
