package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.bookPublisherException.BookPublisherNotFoundException;
import com.librarymanagement.demo.model.BookPublisher;
import com.librarymanagement.demo.repository.BookPublisherRepository;
import com.librarymanagement.demo.service.BookPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookPublisherServiceImpl implements BookPublisherService {

    private final BookPublisherRepository bookPublisherRepository;

    @Autowired
    public BookPublisherServiceImpl(BookPublisherRepository bookPublisherRepository) {
        this.bookPublisherRepository = bookPublisherRepository;
    }

    @Override
    public BookPublisher addPublisher(BookPublisher publisher) {
        return bookPublisherRepository.save(publisher);
    }

    @Override
    public BookPublisher getPublisherById(int publisherId) {
        return bookPublisherRepository.findById(publisherId)
                .orElseThrow(() -> new BookPublisherNotFoundException("Publisher not found with ID: " + publisherId));
    }

    @Override
    public List<BookPublisher> getAllPublishers() {
        return bookPublisherRepository.findAll();
    }

    @Override
    public BookPublisher updatePublisher(int id, BookPublisher updatedPublisher) {
        BookPublisher existing = bookPublisherRepository.findById(id)
                .orElseThrow(() -> new BookPublisherNotFoundException("Publisher not found with ID: " + id));

        existing.setName(updatedPublisher.getName());
        existing.setContactNumber(updatedPublisher.getContactNumber());
        existing.setWebsite(updatedPublisher.getWebsite());
        existing.setAddress(updatedPublisher.getAddress());

        return bookPublisherRepository.save(existing);
    }

    @Override
    public void deletePublisher(int publisherId) {
        BookPublisher publisher = bookPublisherRepository.findById(publisherId)
                .orElseThrow(() -> new BookPublisherNotFoundException("Publisher not found with ID: " + publisherId));
        bookPublisherRepository.delete(publisher);
    }
}
