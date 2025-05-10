package com.librarymanagement.demo.service.impl;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.exception.storeException.StoreNotFoundException;
import com.librarymanagement.demo.model.Store;
import com.librarymanagement.demo.repository.StoreRepository;
import com.librarymanagement.demo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(StoreServiceImpl.class);
    private final StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public void addStore(Store store) {
        logger.info("🔽 Entered addStore()");
        storeRepository.save(store);
        logger.info("✅ Exiting addStore()");
    }

    @Override
    public Store getStoreById(int storeId) {
        logger.info("🔽 Entered getStoreById() with id: {}", storeId);
        Store store = storeRepository.findById(storeId);
        if (store == null) {
            logger.error("❌ Store with ID {} not found", storeId);
            throw new StoreNotFoundException("Store with ID " + storeId + " not found");
        }
        logger.info("✅ Exiting getStoreById()");
        return store;
    }

    @Override
    public List<Store> getAllStores() {
        logger.info("🔽 Entered getAllStores()");
        List<Store> stores = storeRepository.findAll();
        logger.info("✅ Exiting getAllStores()");
        return stores;
    }

    @Override
    public void updateStore(Store updatedStore) {
        logger.info("🔽 Entered updateStore() with id: {}", updatedStore.getStoreId());
        if (!storeRepository.existsById(updatedStore.getStoreId())) {
            logger.error("❌ Store with ID {} not found", updatedStore.getStoreId());
            throw new StoreNotFoundException("Store with ID " + updatedStore.getStoreId() + " not found");
        }
        storeRepository.update(updatedStore);
        logger.info("✅ Exiting updateStore()");
    }

    @Override
    public void deleteStore(int storeId) {
        logger.info("🔽 Entered deleteStore() with id: {}", storeId);
        if (!storeRepository.existsById(storeId)) {
            logger.error("❌ Store with ID {} not found", storeId);
            throw new StoreNotFoundException("Store with ID " + storeId + " not found");
        }
        storeRepository.deleteById(storeId);
        logger.info("✅ Exiting deleteStore()");
    }
}
