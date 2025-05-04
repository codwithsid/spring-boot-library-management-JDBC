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
        Store store = storeRepository.findById(storeId);
        if (store == null) {
            throw new StoreNotFoundException("Store with ID " + storeId + " not found");
        }
        return store;
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public void updateStore(Store updatedStore) {
        if (!storeRepository.existsById(updatedStore.getStoreId())) {
            throw new StoreNotFoundException("Store with ID " + updatedStore.getStoreId() + " not found");
        }
        storeRepository.update(updatedStore);
    }

    @Override
    public void deleteStore(int storeId) {
        if (!storeRepository.existsById(storeId)) {
            throw new StoreNotFoundException("Store with ID " + storeId + " not found");
        }
        storeRepository.deleteById(storeId);
    }
}
