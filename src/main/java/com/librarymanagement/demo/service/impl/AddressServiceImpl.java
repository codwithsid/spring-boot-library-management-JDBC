package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.addressException.AddressNotFoundException;
import com.librarymanagement.demo.model.Address;
import com.librarymanagement.demo.repository.AddressRepository;
import com.librarymanagement.demo.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressById(int id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID: " + id));
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address updateAddress(int id, Address updatedAddress) {
        Address existing = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Cannot update. Address not found with ID: " + id));

        existing.setStreet(updatedAddress.getStreet());
        existing.setCity(updatedAddress.getCity());
        existing.setState(updatedAddress.getState());
        existing.setCountry(updatedAddress.getCountry());
        existing.setPostalCode(updatedAddress.getPostalCode());
        existing.setLandmark(updatedAddress.getLandmark());
        existing.setAddressType(updatedAddress.getAddressType());
        existing.setUser(updatedAddress.getUser());

        return addressRepository.save(existing);
    }

    @Override
    public void deleteAddress(int id) {
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException("Cannot delete. Address not found with ID: " + id);
        }
        addressRepository.deleteById(id);
    }
}
