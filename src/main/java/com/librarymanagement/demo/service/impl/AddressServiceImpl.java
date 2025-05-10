package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.addressException.AddressNotFoundException;
import com.librarymanagement.demo.model.Address;
import com.librarymanagement.demo.repository.AddressRepository;
import com.librarymanagement.demo.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(Address address) {
        logger.info("Saving address...");
        Address saved = addressRepository.save(address);
        logger.info("Address created with ID: {}", saved.getAddressId());
        return saved;
    }

    @Override
    public Address getAddressById(int id) {
        logger.info("Fetching address with ID: {}", id);
        Address found = addressRepository.findById(id);
        if (found == null) {
            logger.error("Address not found with ID: {}", id);
            throw new AddressNotFoundException("Address not found with ID: " + id);
        }
        logger.info("Address fetched successfully");
        return found;
    }

    @Override
    public List<Address> getAllAddresses() {
        logger.info("Fetching all addresses...");
        return addressRepository.findAll();
    }

    @Override
    public Address updateAddress(int id, Address updatedAddress) {
        logger.info("Updating address with ID: {}", id);
        if (!addressRepository.existsById(id)) {
            logger.error("Address not found with ID: {}", id);
            throw new AddressNotFoundException("Cannot update. Address not found with ID: " + id);
        }
        return addressRepository.update(id, updatedAddress);
    }

    @Override
    public void deleteAddress(int id) {
        logger.info("Deleting address with ID: {}", id);
        if (!addressRepository.existsById(id)) {
            logger.error("Address not found with ID: {}", id);
            throw new AddressNotFoundException("Cannot delete. Address not found with ID: " + id);
        }
        addressRepository.deleteById(id);
        logger.info("Address deleted successfully");
    }
}
