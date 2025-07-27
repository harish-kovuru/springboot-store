package com.springbootcode.store.services;

import com.springbootcode.store.dto.OrderDto;
import com.springbootcode.store.exceptions.OrderNotFoundException;
import com.springbootcode.store.mappers.OrderMapper;
import com.springbootcode.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    public List<OrderDto> getOrders(){

        var user = authService.getCurrentUser();
        var order = orderRepository.getAllByCustomer(user);
        return order.stream().map(orderMapper::toDto).toList();


    }

    public OrderDto getOrderByUser(Long orderId) {
        var order = orderRepository.getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        var user = authService.getCurrentUser();
        if(!order.isPlacedBy(user)) {
            throw new AccessDeniedException("you don't have permission to access this resource");

        }
        return orderMapper.toDto(order);
    }
}
