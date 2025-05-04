package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.addressException.AddressNotFoundException;
import com.librarymanagement.demo.model.Address;
import com.librarymanagement.demo.repository.AddressRepository;
import com.librarymanagement.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressById(int id) {
        Address found = addressRepository.findById(id);
        if (found == null) {
            throw new AddressNotFoundException("Address not found with ID: " + id);
        }
        return found;
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address updateAddress(int id, Address updatedAddress) {
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException("Cannot update. Address not found with ID: " + id);
        }
        return addressRepository.update(id, updatedAddress);
    }

    @Override
    public void deleteAddress(int id) {
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException("Cannot delete. Address not found with ID: " + id);
        }
        addressRepository.deleteById(id);
    }
}
