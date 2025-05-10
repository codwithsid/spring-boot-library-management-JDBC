package com.librarymanagement.demo.controller;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.model.Transactions;
import com.librarymanagement.demo.service.TransactionsService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionsController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(TransactionsController.class);
    private static final String CLASS_NAME = "TransactionsController";

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    public ResponseEntity<Transactions> save(@RequestBody Transactions transaction) {
        logger.info("Entering {}.save", CLASS_NAME);
        try {
            Transactions created = transactionsService.createTransaction(transaction);
            logger.info("Exiting {}.save", CLASS_NAME);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            logger.error("Exception in {}.save", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transactions> retrieve(@PathVariable int id) {
        logger.info("Entering {}.retrieve with ID: {}", CLASS_NAME, id);
        try {
            Transactions result = transactionsService.getTransactionById(id);
            logger.info("Exiting {}.retrieve", CLASS_NAME);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Exception in {}.retrieve", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<Transactions>> retrieveAll() {
        logger.info("Entering {}.retrieveAll", CLASS_NAME);
        try {
            List<Transactions> transactions = transactionsService.getAllTransactions();
            logger.info("Exiting {}.retrieveAll", CLASS_NAME);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Exception in {}.retrieveAll", CLASS_NAME, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transactions> update(@PathVariable int id, @RequestBody Transactions transaction) {
        logger.info("Entering {}.update with ID: {}", CLASS_NAME, id);
        try {
            transaction.setTransactionId(id);
            Transactions updated = transactionsService.updateTransaction(transaction);
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
            transactionsService.deleteTransaction(id);
            logger.info("Exiting {}.delete", CLASS_NAME);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Exception in {}.delete", CLASS_NAME, e);
            throw e;
        }
    }
}
