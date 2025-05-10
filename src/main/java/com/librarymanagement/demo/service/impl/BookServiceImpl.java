package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.bookException.BookNotFoundException;
import com.librarymanagement.demo.model.Book;
import com.librarymanagement.demo.repository.BookRepository;
import com.librarymanagement.demo.service.BookService;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBook(Book book) {
        logger.info("Entering addBook()");
        bookRepository.save(book);
        logger.info("Exiting addBook()");
    }

    @Override
    public Book getBookById(int bookId) throws BookNotFoundException {
        logger.info("Entering getBookById() with id: {}", bookId);
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            logger.error("Book not found with id: {}", bookId);
            throw new BookNotFoundException("Book not found with id: " + bookId);
        }
        logger.info("Exiting getBookById()");
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        logger.info("Entering getAllBooks()");
        List<Book> books = bookRepository.findAll();
        logger.info("Exiting getAllBooks()");
        return books;
    }

    @Override
    public void updateBook(Book updatedBook) throws BookNotFoundException {
        logger.info("Entering updateBook() with id: {}", updatedBook.getBookId());
        if (!bookRepository.existsById(updatedBook.getBookId())) {
            logger.error("Book not found with id: {}", updatedBook.getBookId());
            throw new BookNotFoundException("Book not found with id: " + updatedBook.getBookId());
        }
        bookRepository.update(updatedBook);
        logger.info("Exiting updateBook()");
    }

    @Override
    public void deleteBook(int bookId) throws BookNotFoundException {
        logger.info("Entering deleteBook() with id: {}", bookId);
        if (!bookRepository.existsById(bookId)) {
            logger.error("Book not found with id: {}", bookId);
            throw new BookNotFoundException("Book not found with id: " + bookId);
        }
        bookRepository.deleteById(bookId);
        logger.info("Exiting deleteBook()");
    }
}
