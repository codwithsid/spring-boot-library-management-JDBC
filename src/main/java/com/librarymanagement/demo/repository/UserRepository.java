package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
        User findByemailId(String emailId);
        User findBymobileNumber(String mobileNumber);

    }
