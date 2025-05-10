package com.librarymanagement.demo.service.impl;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.exception.authorException.AuthorNotFoundException;
import com.librarymanagement.demo.model.Author;
import com.librarymanagement.demo.repository.AuthorRepository;
import com.librarymanagement.demo.service.AuthorService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author addAuthor(Author author) {
        logger.info("Entering addAuthor");
        try {
            Author saved = authorRepository.save(author);
            logger.info("Exiting addAuthor");
            return saved;
        } catch (Exception e) {
            logger.error("Exception in addAuthor", e);
            throw e;
        }
    }

    @Override
    public Author getAuthorById(int authorId) {
        logger.info("Entering getAuthorById with ID: {}", authorId);
        try {
            Author author = authorRepository.findById(authorId);
            if (author == null) {
                throw new AuthorNotFoundException("Author not found with ID: " + authorId);
            }
            logger.info("Exiting getAuthorById");
            return author;
        } catch (Exception e) {
            logger.error("Exception in getAuthorById", e);
            throw e;
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        logger.info("Entering getAllAuthors");
        try {
            List<Author> authors = authorRepository.findAll();
            logger.info("Exiting getAllAuthors");
            return authors;
        } catch (Exception e) {
            logger.error("Exception in getAllAuthors", e);
            throw e;
        }
    }

    @Override
    public Author updateAuthor(Author author) {
        logger.info("Entering updateAuthor with ID: {}", author.getAuthorId());
        try {
            if (!authorRepository.existsById(author.getAuthorId())) {
                throw new AuthorNotFoundException("Cannot update non-existing author with ID: " + author.getAuthorId());
            }
            Author updated = authorRepository.save(author);
            logger.info("Exiting updateAuthor");
            return updated;
        } catch (Exception e) {
            logger.error("Exception in updateAuthor", e);
            throw e;
        }
    }

    @Override
    public void deleteAuthor(int authorId) {
        logger.info("Entering deleteAuthor with ID: {}", authorId);
        try {
            if (!authorRepository.existsById(authorId)) {
                throw new AuthorNotFoundException("Cannot delete non-existing author with ID: " + authorId);
            }
            authorRepository.deleteById(authorId);
            logger.info("Exiting deleteAuthor");
        } catch (Exception e) {
            logger.error("Exception in deleteAuthor", e);
            throw e;
        }
    }

    @Override
    public List<Author> searchAuthorsByName(String authorName) {
        logger.info("Entering searchAuthorsByName with name: {}", authorName);
        try {
            List<Author> authors = authorRepository.findByNameLike(authorName);
            logger.info("Exiting searchAuthorsByName");
            return authors;
        } catch (Exception e) {
            logger.error("Exception in searchAuthorsByName", e);
            throw e;
        }
    }
}
