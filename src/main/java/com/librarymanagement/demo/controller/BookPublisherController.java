package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.BookPublisher;
import com.librarymanagement.demo.service.BookPublisherService;
import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
public class BookPublisherController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookPublisherController.class);
    private static final String CLASS_NAME = "BookPublisherController";

    private final BookPublisherService bookPublisherService;

    @Autowired
    public BookPublisherController(BookPublisherService bookPublisherService) {
        this.bookPublisherService = bookPublisherService;
    }

    @PostMapping
    public BookPublisher save(@RequestBody BookPublisher publisher) {
        logger.info("Entering {}.save", CLASS_NAME);
        try {
            BookPublisher result = bookPublisherService.addPublisher(publisher);
            logger.info("Exiting {}.save", CLASS_NAME);
            return result;
        } catch (Exception e) {
            logger.error("Exception in {}.save", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public BookPublisher retrieve(@PathVariable int id) {
        logger.info("Entering {}.retrieve with id={}", CLASS_NAME, id);
        try {
            BookPublisher result = bookPublisherService.getPublisherById(id);
            logger.info("Exiting {}.retrieve", CLASS_NAME);
            return result;
        } catch (Exception e) {
            logger.error("Exception in {}.retrieve", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping
    public List<BookPublisher> retrieveAll() {
        logger.info("Entering {}.retrieveAll", CLASS_NAME);
        try {
            List<BookPublisher> result = bookPublisherService.getAllPublishers();
            logger.info("Exiting {}.retrieveAll", CLASS_NAME);
            return result;
        } catch (Exception e) {
            logger.error("Exception in {}.retrieveAll", CLASS_NAME, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public BookPublisher update(@PathVariable int id, @RequestBody BookPublisher publisher) {
        logger.info("Entering {}.update with id={}", CLASS_NAME, id);
        try {
            BookPublisher result = bookPublisherService.updatePublisher(id, publisher);
            logger.info("Exiting {}.update", CLASS_NAME);
            return result;
        } catch (Exception e) {
            logger.error("Exception in {}.update", CLASS_NAME, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        logger.info("Entering {}.delete with id={}", CLASS_NAME, id);
        try {
            bookPublisherService.deletePublisher(id);
            logger.info("Exiting {}.delete", CLASS_NAME);
        } catch (Exception e) {
            logger.error("Exception in {}.delete", CLASS_NAME, e);
            throw e;
        }
    }
}
