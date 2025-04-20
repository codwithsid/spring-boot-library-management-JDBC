package com.librarymanagement.demo.model;

public enum USER_ROLE {
    ROLE_CUSTOMER,   // Regular library users
    ROLE_PUBLISHER,  // Publishers adding book details
    ROLE_ADMIN,      // Library administrator
    ROLE_LIBRARIAN,  // Manages book inventory
    ROLE_MODERATOR,  // Approves book reviews
    ROLE_GUEST,      // Can browse books but not borrow
    ROLE_SUPER_ADMIN // Full system control
}
