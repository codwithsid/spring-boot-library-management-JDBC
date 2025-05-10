package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.exception.bookException.BookNotFoundException;
import com.librarymanagement.demo.model.Book;
import com.librarymanagement.demo.service.BookService;
import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookController.class);
    private static final String CLASS_NAME = "BookController";

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Book book) {
        logger.info("Entering {}.save", CLASS_NAME);
        bookService.addBook(book);
        logger.info("Exiting {}.save", CLASS_NAME);
        return ResponseEntity.ok("Book added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieve(@PathVariable int id) {
        logger.info("Entering {}.retrieve with ID: {}", CLASS_NAME, id);
        try {
            ResponseEntity<?> response = ResponseEntity.ok(bookService.getBookById(id));
            logger.info("Exiting {}.retrieve", CLASS_NAME);
            return response;
        } catch (BookNotFoundException e) {
            logger.error("BookNotFoundException in {}.retrieve: {}", CLASS_NAME, e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Book> retrieveAll() {
        logger.info("Entering {}.retrieveAll", CLASS_NAME);
        List<Book> books = bookService.getAllBooks();
        logger.info("Exiting {}.retrieveAll", CLASS_NAME);
        return books;
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody Book book) {
        logger.info("Entering {}.update", CLASS_NAME);
        try {
            bookService.updateBook(book);
            logger.info("Exiting {}.update", CLASS_NAME);
            return ResponseEntity.ok("Book updated successfully");
        } catch (BookNotFoundException e) {
            logger.error("BookNotFoundException in {}.update: {}", CLASS_NAME, e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        logger.info("Entering {}.delete with ID: {}", CLASS_NAME, id);
        try {
            bookService.deleteBook(id);
            logger.info("Exiting {}.delete", CLASS_NAME);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (BookNotFoundException e) {
            logger.error("BookNotFoundException in {}.delete: {}", CLASS_NAME, e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
