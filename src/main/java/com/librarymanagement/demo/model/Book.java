package com.librarymanagement.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String title;
    private String isbn;
    private LocalDate publishDate;
    private int totalCopies;
    private int availableCopies;
    private String category;
    private String language;
    private double price;
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private BookPublisher publisher;
}
