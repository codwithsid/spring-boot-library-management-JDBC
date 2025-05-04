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
        BookPublisher publisher = bookPublisherRepository.findById(publisherId);
        if (publisher == null) {
            throw new BookPublisherNotFoundException("Publisher not found with ID: " + publisherId);
        }
        return publisher;
    }

    @Override
    public List<BookPublisher> getAllPublishers() {
        return bookPublisherRepository.findAll();
    }

    @Override
    public BookPublisher updatePublisher(int id, BookPublisher updatedPublisher) {
        if (!bookPublisherRepository.existsById(id)) {
            throw new BookPublisherNotFoundException("Publisher not found with ID: " + id);
        }
        return bookPublisherRepository.update(updatedPublisher);
    }

    @Override
    public void deletePublisher(int publisherId) {
        if (!bookPublisherRepository.existsById(publisherId)) {
            throw new BookPublisherNotFoundException("Publisher not found with ID: " + publisherId);
        }
        bookPublisherRepository.deleteById(publisherId);
    }
}
