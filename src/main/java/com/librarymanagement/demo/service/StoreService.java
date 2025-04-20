package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Store;

import java.util.List;

public interface StoreService {
    void addStore(Store store);
    Store getStoreById(int storeId);
    List<Store> getAllStores();
    void updateStore(Store store);
    void deleteStore(int storeId);
}
