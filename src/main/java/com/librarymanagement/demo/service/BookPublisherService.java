package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.BookPublisher;

import java.util.List;

public interface BookPublisherService {
    BookPublisher addPublisher(BookPublisher publisher);
    BookPublisher getPublisherById(int publisherId);
    List<BookPublisher> getAllPublishers();
    BookPublisher updatePublisher(int id, BookPublisher publisher);
    void deletePublisher(int publisherId);
}
