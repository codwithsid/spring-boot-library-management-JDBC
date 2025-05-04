package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Billing;
import com.librarymanagement.demo.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public ResponseEntity<Billing> createBilling(@RequestBody Billing billing) {
        return ResponseEntity.ok(billingService.createBilling(billing));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> getBillingById(@PathVariable int id) {
        return ResponseEntity.ok(billingService.getBillingById(id));
    }

    @GetMapping
    public ResponseEntity<List<Billing>> getAllBillings() {
        return ResponseEntity.ok(billingService.getAllBillings());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Billing> updateBilling(@PathVariable int id, @RequestBody Billing billing) {
        billing.setBillingId(id);
        return ResponseEntity.ok(billingService.updateBilling(billing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBilling(@PathVariable int id) {
        billingService.deleteBilling(id);
        return ResponseEntity.noContent().build();
    }
}
