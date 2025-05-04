package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.authorException.AuthorNotFoundException;
import com.librarymanagement.demo.model.Author;
import com.librarymanagement.demo.repository.AuthorRepository;
import com.librarymanagement.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthorById(int authorId) {
        Author author = authorRepository.findById(authorId);
        if (author == null) {
            throw new AuthorNotFoundException("Author not found with ID: " + authorId);
        }
        return author;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author updateAuthor(Author author) {
        if (!authorRepository.existsById(author.getAuthorId())) {
            throw new AuthorNotFoundException("Cannot update non-existing author with ID: " + author.getAuthorId());
        }
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(int authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new AuthorNotFoundException("Cannot delete non-existing author with ID: " + authorId);
        }
        authorRepository.deleteById(authorId);
    }

    @Override
    public List<Author> searchAuthorsByName(String authorName) {
        return authorRepository.findByNameLike(authorName);
    }
}
