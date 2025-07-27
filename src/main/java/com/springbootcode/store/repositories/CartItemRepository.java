package com.springbootcode.store.repositories;

import com.springbootcode.store.entities.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
}
