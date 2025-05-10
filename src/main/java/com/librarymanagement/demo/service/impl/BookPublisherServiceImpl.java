package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.bookPublisherException.BookPublisherNotFoundException;
import com.librarymanagement.demo.model.BookPublisher;
import com.librarymanagement.demo.repository.BookPublisherRepository;
import com.librarymanagement.demo.service.BookPublisherService;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookPublisherServiceImpl implements BookPublisherService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookPublisherServiceImpl.class);

    private final BookPublisherRepository bookPublisherRepository;

    @Autowired
    public BookPublisherServiceImpl(BookPublisherRepository bookPublisherRepository) {
        this.bookPublisherRepository = bookPublisherRepository;
    }

    @Override
    public BookPublisher addPublisher(BookPublisher publisher) {
        logger.info("Entered addPublisher");
        try {
            BookPublisher result = bookPublisherRepository.save(publisher);
            logger.info("Exiting addPublisher");
            return result;
        } catch (Exception e) {
            logger.error("Exception in addPublisher: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public BookPublisher getPublisherById(int publisherId) {
        logger.info("Entered getPublisherById with id={}", publisherId);
        try {
            BookPublisher publisher = bookPublisherRepository.findById(publisherId);
            if (publisher == null) {
                throw new BookPublisherNotFoundException("Publisher not found with ID: " + publisherId);
            }
            logger.info("Exiting getPublisherById");
            return publisher;
        } catch (Exception e) {
            logger.error("Exception in getPublisherById: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<BookPublisher> getAllPublishers() {
        logger.info("Entered getAllPublishers");
        try {
            List<BookPublisher> result = bookPublisherRepository.findAll();
            logger.info("Exiting getAllPublishers");
            return result;
        } catch (Exception e) {
            logger.error("Exception in getAllPublishers: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public BookPublisher updatePublisher(int id, BookPublisher updatedPublisher) {
        logger.info("Entered updatePublisher with id={}", id);
        try {
            if (!bookPublisherRepository.existsById(id)) {
                throw new BookPublisherNotFoundException("Publisher not found with ID: " + id);
            }
            BookPublisher result = bookPublisherRepository.update(updatedPublisher);
            logger.info("Exiting updatePublisher");
            return result;
        } catch (Exception e) {
            logger.error("Exception in updatePublisher: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deletePublisher(int publisherId) {
        logger.info("Entered deletePublisher with id={}", publisherId);
        try {
            if (!bookPublisherRepository.existsById(publisherId)) {
                throw new BookPublisherNotFoundException("Publisher not found with ID: " + publisherId);
            }
            bookPublisherRepository.deleteById(publisherId);
            logger.info("Exiting deletePublisher");
        } catch (Exception e) {
            logger.error("Exception in deletePublisher: {}", e.getMessage(), e);
            throw e;
        }
    }
}
