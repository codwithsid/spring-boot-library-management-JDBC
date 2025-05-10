package com.librarymanagement.demo.controller;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.model.Address;
import com.librarymanagement.demo.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AddressController.class);
    private static final String CLASS_NAME = "AddressController";

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address address) {
        logger.info("Entering {}.save", CLASS_NAME);
        try {
            Address saved = addressService.createAddress(address);
            logger.info("Exiting {}.save", CLASS_NAME);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            logger.error("Exception in {}.save", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> retrieve(@PathVariable int id) {
        logger.info("Entering {}.retrieve with ID: {}", CLASS_NAME, id);
        try {
            Address found = addressService.getAddressById(id);
            logger.info("Exiting {}.retrieve", CLASS_NAME);
            return ResponseEntity.ok(found);
        } catch (Exception e) {
            logger.error("Exception in {}.retrieve", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<Address>> retrieveAll() {
        logger.info("Entering {}.retrieveAll", CLASS_NAME);
        try {
            List<Address> addresses = addressService.getAllAddresses();
            logger.info("Exiting {}.retrieveAll", CLASS_NAME);
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            logger.error("Exception in {}.retrieveAll", CLASS_NAME, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable int id, @RequestBody Address address) {
        logger.info("Entering {}.update with ID: {}", CLASS_NAME, id);
        try {
            Address updated = addressService.updateAddress(id, address);
            logger.info("Exiting {}.update", CLASS_NAME);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("Exception in {}.update", CLASS_NAME, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        logger.info("Entering {}.delete with ID: {}", CLASS_NAME, id);
        try {
            addressService.deleteAddress(id);
            logger.info("Exiting {}.delete", CLASS_NAME);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Exception in {}.delete", CLASS_NAME, e);
            throw e;
        }
    }
}
