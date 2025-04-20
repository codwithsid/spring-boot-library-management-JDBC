package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Transactions;

import java.util.List;

public interface TransactionsService {
    Transactions createTransaction(Transactions transaction);
    Transactions getTransactionById(int transactionId);
    List<Transactions> getAllTransactions();
    Transactions updateTransaction(Transactions transaction);
    void deleteTransaction(int transactionId);
}
