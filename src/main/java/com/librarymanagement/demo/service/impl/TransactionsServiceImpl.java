package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.transactionException.TransactionNotFoundException;
import com.librarymanagement.demo.model.Transactions;
import com.librarymanagement.demo.repository.TransactionsRepository;
import com.librarymanagement.demo.service.TransactionsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private final TransactionsRepository transactionsRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public Transactions createTransaction(Transactions transaction) {
        return transactionsRepository.save(transaction);
    }

    @Override
    public Transactions getTransactionById(int transactionId) {
        return transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + transactionId));
    }

    @Override
    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    @Override
    public Transactions updateTransaction(Transactions transaction) {
        if (!transactionsRepository.existsById(transaction.getTransactionId())) {
            throw new TransactionNotFoundException("Cannot update non-existing transaction with ID: " + transaction.getTransactionId());
        }
        return transactionsRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(int transactionId) {
        if (!transactionsRepository.existsById(transactionId)) {
            throw new TransactionNotFoundException("Cannot delete non-existing transaction with ID: " + transactionId);
        }
        transactionsRepository.deleteById(transactionId);
    }
}
