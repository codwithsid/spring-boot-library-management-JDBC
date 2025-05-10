package com.librarymanagement.demo.controller;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.exception.userException.UserNotFoundException;
import com.librarymanagement.demo.model.User;
import com.librarymanagement.demo.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);
    private static final String CLASS_NAME = "UserController";

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> save(@RequestBody User user) {
        logger.info("Entering {}.save", CLASS_NAME);
        if (user.getAddress() != null) {
            user.getAddress().setUser(user);
        }
        userService.registerUser(user);
        logger.info("Exiting {}.save", CLASS_NAME);
        return ResponseEntity.ok("✅ User registered successfully!");
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> retrieveAll() {
        logger.info("Entering {}.retrieveAll", CLASS_NAME);
        List<User> users = userService.getAllUsers();
        logger.info("Exiting {}.retrieveAll", CLASS_NAME);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieve(@PathVariable int id) throws UserNotFoundException {
        logger.info("Entering {}.retrieve with ID: {}", CLASS_NAME, id);
        User user = userService.getUserById(id);
        logger.info("Exiting {}.retrieve", CLASS_NAME);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody User updatedUser) {
        logger.info("Entering {}.update with ID: {}", CLASS_NAME, id);
        try {
            User updated = userService.updateUserByuserId(updatedUser, id);
            logger.info("Exiting {}.update", CLASS_NAME);
            return ResponseEntity.ok(updated);
        } catch (UserNotFoundException e) {
            logger.error("Exception in {}.update: {}", CLASS_NAME, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int userId) {
        logger.info("Entering {}.delete with ID: {}", CLASS_NAME, userId);
        try {
            userService.deleteUser(userId);
        } catch (UserNotFoundException e) {
            logger.error("Exception in {}.delete: {}", CLASS_NAME, e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("Exiting {}.delete", CLASS_NAME);
        return ResponseEntity.ok("✅ User deleted successfully!");
    }
}
