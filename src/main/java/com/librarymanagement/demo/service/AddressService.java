package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Address;

import java.util.List;

public interface AddressService {
    Address createAddress(Address address);
    Address getAddressById(int id);
    List<Address> getAllAddresses();
    Address updateAddress(int id, Address updatedAddress);
    void deleteAddress(int id);
}
