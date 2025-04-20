package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.storeException.StoreNotFoundException;
import com.librarymanagement.demo.model.Store;
import com.librarymanagement.demo.repository.StoreRepository;
import com.librarymanagement.demo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public void addStore(Store store) {
        storeRepository.save(store);
    }

    @Override
    public Store getStoreById(int storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("Store with ID " + storeId + " not found"));
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public void updateStore(Store store) {
        if (!storeRepository.existsById(store.getStoreId())) {
            throw new StoreNotFoundException("Store with ID " + store.getStoreId() + " not found");
        }
        storeRepository.save(store);
    }

    @Override
    public void deleteStore(int storeId) {
        if (!storeRepository.existsById(storeId)) {
            throw new StoreNotFoundException("Store with ID " + storeId + " not found");
        }
        storeRepository.deleteById(storeId);
    }
}
