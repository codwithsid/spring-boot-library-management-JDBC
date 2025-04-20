package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.BookPublisher;
import com.librarymanagement.demo.service.BookPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class BookPublisherController {

    private final BookPublisherService bookPublisherService;

    @Autowired
    public BookPublisherController(BookPublisherService bookPublisherService) {
        this.bookPublisherService = bookPublisherService;
    }

    @PostMapping
    public BookPublisher createPublisher(@RequestBody BookPublisher publisher) {
        return bookPublisherService.addPublisher(publisher);
    }

    @GetMapping("/{id}")
    public BookPublisher getPublisherById(@PathVariable int id) {
        return bookPublisherService.getPublisherById(id);
    }

    @GetMapping
    public List<BookPublisher> getAllPublishers() {
        return bookPublisherService.getAllPublishers();
    }

    @PutMapping("/{id}")
    public BookPublisher updatePublisher(@PathVariable int id, @RequestBody BookPublisher publisher) {
        return bookPublisherService.updatePublisher(id, publisher);
    }

    @DeleteMapping("/{id}")
    public void deletePublisher(@PathVariable int id) {
        bookPublisherService.deletePublisher(id);
    }
}
