package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Book;
import com.librarymanagement.demo.exception.bookException.BookNotFoundException;

import java.util.List;

public interface BookService {
    void addBook(Book book);
    Book getBookById(int bookId) throws BookNotFoundException;
    List<Book> getAllBooks();
    void updateBook(Book book) throws BookNotFoundException;
    void deleteBook(int bookId) throws BookNotFoundException;
}
