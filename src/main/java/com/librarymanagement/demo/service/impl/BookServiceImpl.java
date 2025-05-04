package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.bookException.BookNotFoundException;
import com.librarymanagement.demo.model.Book;
import com.librarymanagement.demo.repository.BookRepository;
import com.librarymanagement.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getBookById(int bookId) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book not found with id: " + bookId);
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void updateBook(Book updatedBook) throws BookNotFoundException {
        if (!bookRepository.existsById(updatedBook.getBookId())) {
            throw new BookNotFoundException("Book not found with id: " + updatedBook.getBookId());
        }
        bookRepository.update(updatedBook);
    }

    @Override
    public void deleteBook(int bookId) throws BookNotFoundException {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException("Book not found with id: " + bookId);
        }
        bookRepository.deleteById(bookId);
    }
}
