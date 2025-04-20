package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.billingException.BillNotFoundException;
import com.librarymanagement.demo.model.Billing;
import com.librarymanagement.demo.repository.BillingRepository;
import com.librarymanagement.demo.service.BillingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;

    public BillingServiceImpl(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @Override
    public Billing createBilling(Billing billing) {
        return billingRepository.save(billing);
    }

    @Override
    public Billing getBillingById(int billingId) {
        return billingRepository.findById(billingId)
                .orElseThrow(() -> new BillNotFoundException("Billing record not found with ID: " + billingId));
    }

    @Override
    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    @Override
    public Billing updateBilling(Billing billing) {
        if (!billingRepository.existsById(billing.getBillingId())) {
            throw new BillNotFoundException("Cannot update. Billing ID not found: " + billing.getBillingId());
        }
        return billingRepository.save(billing);
    }

    @Override
    public void deleteBilling(int billingId) {
        if (!billingRepository.existsById(billingId)) {
            throw new BillNotFoundException("Cannot delete. Billing ID not found: " + billingId);
        }
        billingRepository.deleteById(billingId);
    }
}
