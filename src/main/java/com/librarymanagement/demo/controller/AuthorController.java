package com.librarymanagement.demo.controller;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.model.Author;
import com.librarymanagement.demo.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AuthorController.class);
    private static final String CLASS_NAME = "AuthorController";

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Author> save(@RequestBody Author author) {
        logger.info("Entering {}.save", CLASS_NAME);
        try {
            Author savedAuthor = authorService.addAuthor(author);
            logger.info("Exiting {}.save", CLASS_NAME);
            return ResponseEntity.ok(savedAuthor);
        } catch (Exception e) {
            logger.error("Exception in {}.save", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> retrieve(@PathVariable int id) {
        logger.info("Entering {}.retrieve with ID: {}", CLASS_NAME, id);
        try {
            Author author = authorService.getAuthorById(id);
            logger.info("Exiting {}.retrieve", CLASS_NAME);
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            logger.error("Exception in {}.retrieve", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<Author>> retrieveAll() {
        logger.info("Entering {}.retrieveAll", CLASS_NAME);
        try {
            List<Author> authors = authorService.getAllAuthors();
            logger.info("Exiting {}.retrieveAll", CLASS_NAME);
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            logger.error("Exception in {}.retrieveAll", CLASS_NAME, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author) {
        logger.info("Entering {}.update with ID: {}", CLASS_NAME, id);
        try {
            author.setAuthorId(id);
            Author updatedAuthor = authorService.updateAuthor(author);
            logger.info("Exiting {}.update", CLASS_NAME);
            return ResponseEntity.ok(updatedAuthor);
        } catch (Exception e) {
            logger.error("Exception in {}.update", CLASS_NAME, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        logger.info("Entering {}.delete with ID: {}", CLASS_NAME, id);
        try {
            authorService.deleteAuthor(id);
            logger.info("Exiting {}.delete", CLASS_NAME);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Exception in {}.delete", CLASS_NAME, e);
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Author>> search(@RequestParam String name) {
        logger.info("Entering {}.search with name: {}", CLASS_NAME, name);
        try {
            List<Author> authors = authorService.searchAuthorsByName(name);
            logger.info("Exiting {}.search", CLASS_NAME);
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            logger.error("Exception in {}.search", CLASS_NAME, e);
            throw e;
        }
    }
}
