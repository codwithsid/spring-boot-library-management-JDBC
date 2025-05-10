package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.billingException.BillNotFoundException;
import com.librarymanagement.demo.model.Billing;
import com.librarymanagement.demo.repository.BillingRepository;
import com.librarymanagement.demo.service.BillingService;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BillingServiceImpl.class);
    private final BillingRepository billingRepository;

    @Autowired
    public BillingServiceImpl(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @Override
    public Billing createBilling(Billing billing) {
        logger.info("Entering createBilling()");
        try {
            Billing saved = billingRepository.save(billing);
            logger.info("Exiting createBilling()");
            return saved;
        } catch (Exception e) {
            logger.error("Exception in createBilling()", e);
            throw e;
        }
    }

    @Override
    public Billing getBillingById(int billingId) {
        logger.info("Entering getBillingById() with id={}", billingId);
        try {
            Billing billing = billingRepository.findById(billingId);
            if (billing == null) {
                throw new BillNotFoundException("Billing record not found with ID: " + billingId);
            }
            logger.info("Exiting getBillingById()");
            return billing;
        } catch (Exception e) {
            logger.error("Exception in getBillingById()", e);
            throw e;
        }
    }

    @Override
    public List<Billing> getAllBillings() {
        logger.info("Entering getAllBillings()");
        try {
            List<Billing> billings = billingRepository.findAll();
            logger.info("Exiting getAllBillings()");
            return billings;
        } catch (Exception e) {
            logger.error("Exception in getAllBillings()", e);
            throw e;
        }
    }

    @Override
    public Billing updateBilling(Billing billing) {
        logger.info("Entering updateBilling() with id={}", billing.getBillingId());
        try {
            if (!billingRepository.existsById(billing.getBillingId())) {
                throw new BillNotFoundException("Cannot update. Billing ID not found: " + billing.getBillingId());
            }
            Billing updated = billingRepository.update(billing);
            logger.info("Exiting updateBilling()");
            return updated;
        } catch (Exception e) {
            logger.error("Exception in updateBilling()", e);
            throw e;
        }
    }

    @Override
    public void deleteBilling(int billingId) {
        logger.info("Entering deleteBilling() with id={}", billingId);
        try {
            if (!billingRepository.existsById(billingId)) {
                throw new BillNotFoundException("Cannot delete. Billing ID not found: " + billingId);
            }
            billingRepository.deleteById(billingId);
            logger.info("Exiting deleteBilling()");
        } catch (Exception e) {
            logger.error("Exception in deleteBilling()", e);
            throw e;
        }
    }
}
