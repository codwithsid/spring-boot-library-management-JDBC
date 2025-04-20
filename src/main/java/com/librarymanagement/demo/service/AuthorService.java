package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Author;

import java.util.List;

public interface AuthorService {
    Author addAuthor(Author author);
    Author getAuthorById(int authorId);
    List<Author> getAllAuthors();
    Author updateAuthor(Author author);
    void deleteAuthor(int authorId);
    List<Author> searchAuthorsByName(String authorName);
}
