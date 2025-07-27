package com.springbootcode.store.repositories;


import com.springbootcode.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}