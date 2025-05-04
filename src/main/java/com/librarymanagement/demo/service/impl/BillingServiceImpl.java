package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.billingException.BillNotFoundException;
import com.librarymanagement.demo.model.Billing;
import com.librarymanagement.demo.repository.BillingRepository;
import com.librarymanagement.demo.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;

    @Autowired
    public BillingServiceImpl(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @Override
    public Billing createBilling(Billing billing) {
        return billingRepository.save(billing);
    }

    @Override
    public Billing getBillingById(int billingId) {
        Billing billing = billingRepository.findById(billingId);
        if (billing == null) {
            throw new BillNotFoundException("Billing record not found with ID: " + billingId);
        }
        return billing;
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
        return billingRepository.update(billing);
    }

    @Override
    public void deleteBilling(int billingId) {
        if (!billingRepository.existsById(billingId)) {
            throw new BillNotFoundException("Cannot delete. Billing ID not found: " + billingId);
        }
        billingRepository.deleteById(billingId);
    }
}
