package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Billing;

import java.util.List;

public interface BillingService {
    Billing createBilling(Billing billing);
    Billing getBillingById(int billingId);
    List<Billing> getAllBillings();
    Billing updateBilling(Billing billing);
    void deleteBilling(int billingId);
}
