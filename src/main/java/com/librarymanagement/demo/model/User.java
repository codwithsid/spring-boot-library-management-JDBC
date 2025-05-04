package com.librarymanagement.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String mobileNumber;
    private LocalDate dob;
    private LocalDateTime createdAt;
    private boolean isActive;
    private String role;
    private String profileImageUrl;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transactions> borrowedBooks;
}
