package com.librarymanagement.demo.controller;


import com.librarymanagement.demo.exception.storeException.StoreNotFoundException;
import com.librarymanagement.demo.model.Store;
import com.librarymanagement.demo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<String> addStore(@RequestBody Store store) {
        storeService.addStore(store);
        return ResponseEntity.ok("✅ Store added successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable int id) {
        try {
            Store store = storeService.getStoreById(id);
            return ResponseEntity.ok(store);
        } catch (StoreNotFoundException ex) {
            return ResponseEntity.status(404).body("❌ " + ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStore(@PathVariable int id, @RequestBody Store store) {
        try {
            store.setStoreId(id);
            storeService.updateStore(store);
            return ResponseEntity.ok("✅ Store updated successfully!");
        } catch (StoreNotFoundException ex) {
            return ResponseEntity.status(404).body("❌ " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable int id) {
        try {
            storeService.deleteStore(id);
            return ResponseEntity.ok("🗑️ Store deleted successfully!");
        } catch (StoreNotFoundException ex) {
            return ResponseEntity.status(404).body("❌ " + ex.getMessage());
        }
    }
}
