package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Transactions;
import com.librarymanagement.demo.service.TransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionsController {

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    public ResponseEntity<Transactions> createTransaction(@RequestBody Transactions transaction) {
        return ResponseEntity.ok(transactionsService.createTransaction(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transactions> getTransaction(@PathVariable int id) {
        return ResponseEntity.ok(transactionsService.getTransactionById(id));
    }

    @GetMapping
    public ResponseEntity<List<Transactions>> getAllTransactions() {
        return ResponseEntity.ok(transactionsService.getAllTransactions());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transactions> updateTransaction(@PathVariable int id, @RequestBody Transactions transaction) {
        transaction.setTransactionId(id);
        return ResponseEntity.ok(transactionsService.updateTransaction(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        transactionsService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
