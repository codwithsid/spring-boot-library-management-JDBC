package com.librarymanagement.demo.controller;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.exception.storeException.StoreNotFoundException;
import com.librarymanagement.demo.model.Store;
import com.librarymanagement.demo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(StoreController.class);
    private static final String CLASS_NAME = "StoreController";

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Store store) {
        logger.info("Entering {}.save", CLASS_NAME);
        storeService.addStore(store);
        logger.info("Exiting {}.save", CLASS_NAME);
        return ResponseEntity.ok("✅ Store added successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieve(@PathVariable int id) {
        logger.info("Entering {}.retrieve with ID: {}", CLASS_NAME, id);
        try {
            Store store = storeService.getStoreById(id);
            logger.info("Exiting {}.retrieve", CLASS_NAME);
            return ResponseEntity.ok(store);
        } catch (StoreNotFoundException ex) {
            logger.error("Exception in {}.retrieve: {}", CLASS_NAME, ex.getMessage());
            return ResponseEntity.status(404).body("❌ " + ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Store>> retrieveAll() {
        logger.info("Entering {}.retrieveAll", CLASS_NAME);
        List<Store> stores = storeService.getAllStores();
        logger.info("Exiting {}.retrieveAll", CLASS_NAME);
        return ResponseEntity.ok(stores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Store store) {
        logger.info("Entering {}.update with ID: {}", CLASS_NAME, id);
        try {
            store.setStoreId(id);
            storeService.updateStore(store);
            logger.info("Exiting {}.update", CLASS_NAME);
            return ResponseEntity.ok("✅ Store updated successfully!");
        } catch (StoreNotFoundException ex) {
            logger.error("Exception in {}.update: {}", CLASS_NAME, ex.getMessage());
            return ResponseEntity.status(404).body("❌ " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        logger.info("Entering {}.delete with ID: {}", CLASS_NAME, id);
        try {
            storeService.deleteStore(id);
            logger.info("Exiting {}.delete", CLASS_NAME);
            return ResponseEntity.ok("🗑️ Store deleted successfully!");
        } catch (StoreNotFoundException ex) {
            logger.error("Exception in {}.delete: {}", CLASS_NAME, ex.getMessage());
            return ResponseEntity.status(404).body("❌ " + ex.getMessage());
        }
    }
}
