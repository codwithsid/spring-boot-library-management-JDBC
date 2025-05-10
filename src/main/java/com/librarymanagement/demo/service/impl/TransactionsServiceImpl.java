package com.librarymanagement.demo.service.impl;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.exception.transactionException.TransactionNotFoundException;
import com.librarymanagement.demo.model.Transactions;
import com.librarymanagement.demo.repository.TransactionsRepository;
import com.librarymanagement.demo.service.TransactionsService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(TransactionsServiceImpl.class);

    private final TransactionsRepository transactionsRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public Transactions createTransaction(Transactions transaction) {
        logger.info("Entered createTransaction()");
        try {
            Transactions saved = transactionsRepository.save(transaction);
            logger.info("Exiting createTransaction()");
            return saved;
        } catch (Exception e) {
            logger.error("Exception in createTransaction(): ", e);
            throw e;
        }
    }

    @Override
    public Transactions getTransactionById(int transactionId) {
        logger.info("Entered getTransactionById() with id: {}", transactionId);
        try {
            Transactions transaction = transactionsRepository.findById(transactionId);
            if (transaction == null) {
                throw new TransactionNotFoundException("Transaction not found with ID: " + transactionId);
            }
            logger.info("Exiting getTransactionById()");
            return transaction;
        } catch (Exception e) {
            logger.error("Exception in getTransactionById(): ", e);
            throw e;
        }
    }

    @Override
    public List<Transactions> getAllTransactions() {
        logger.info("Entered getAllTransactions()");
        try {
            List<Transactions> transactions = transactionsRepository.findAll();
            logger.info("Exiting getAllTransactions()");
            return transactions;
        } catch (Exception e) {
            logger.error("Exception in getAllTransactions(): ", e);
            throw e;
        }
    }

    @Override
    public Transactions updateTransaction(Transactions updatedTransaction) {
        logger.info("Entered updateTransaction() with id: {}", updatedTransaction.getTransactionId());
        try {
            if (!transactionsRepository.existsById(updatedTransaction.getTransactionId())) {
                throw new TransactionNotFoundException("Cannot update non-existing transaction with ID: " + updatedTransaction.getTransactionId());
            }
            Transactions updated = transactionsRepository.update(updatedTransaction);
            logger.info("Exiting updateTransaction()");
            return updated;
        } catch (Exception e) {
            logger.error("Exception in updateTransaction(): ", e);
            throw e;
        }
    }

    @Override
    public void deleteTransaction(int transactionId) {
        logger.info("Entered deleteTransaction() with id: {}", transactionId);
        try {
            if (!transactionsRepository.existsById(transactionId)) {
                throw new TransactionNotFoundException("Cannot delete non-existing transaction with ID: " + transactionId);
            }
            transactionsRepository.deleteById(transactionId);
            logger.info("Exiting deleteTransaction()");
        } catch (Exception e) {
            logger.error("Exception in deleteTransaction(): ", e);
            throw e;
        }
    }
}
