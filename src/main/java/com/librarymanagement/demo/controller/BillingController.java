package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Billing;
import com.librarymanagement.demo.service.BillingService;
import ch.qos.logback.classic.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BillingController.class);
    private static final String CLASS_NAME = "BillingController";

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public ResponseEntity<Billing> save(@RequestBody Billing billing) {
        logger.info("Entering {}.save", CLASS_NAME);
        try {
            Billing created = billingService.createBilling(billing);
            logger.info("Exiting {}.save", CLASS_NAME);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            logger.error("Exception in {}.save", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> retrieve(@PathVariable int id) {
        logger.info("Entering {}.getBillingById with id={}", CLASS_NAME, id);
        try {
            Billing billing = billingService.getBillingById(id);
            logger.info("Exiting {}.getBillingById", CLASS_NAME);
            return ResponseEntity.ok(billing);
        } catch (Exception e) {
            logger.error("Exception in {}.getBillingById", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<Billing>> retrieveAll() {
        logger.info("Entering {}.getAllBillings", CLASS_NAME);
        try {
            List<Billing> billings = billingService.getAllBillings();
            logger.info("Exiting {}.getAllBillings", CLASS_NAME);
            return ResponseEntity.ok(billings);
        } catch (Exception e) {
            logger.error("Exception in {}.getAllBillings", CLASS_NAME, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Billing> update(@PathVariable int id, @RequestBody Billing billing) {
        logger.info("Entering {}.updateBilling with id={}", CLASS_NAME, id);
        try {
            billing.setBillingId(id);
            Billing updated = billingService.updateBilling(billing);
            logger.info("Exiting {}.updateBilling", CLASS_NAME);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("Exception in {}.updateBilling", CLASS_NAME, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        logger.info("Entering {}.deleteBilling with id={}", CLASS_NAME, id);
        try {
            billingService.deleteBilling(id);
            logger.info("Exiting {}.deleteBilling", CLASS_NAME);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Exception in {}.deleteBilling", CLASS_NAME, e);
            throw e;
        }
    }
}
