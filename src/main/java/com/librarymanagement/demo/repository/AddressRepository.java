package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
