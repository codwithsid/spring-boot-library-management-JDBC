package com.librarymanagement.demo.service.impl;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.exception.userException.UserNotFoundException;
import com.librarymanagement.demo.model.User;
import com.librarymanagement.demo.repository.UserRepository;
import com.librarymanagement.demo.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        logger.info("Entering registerUser()");
        userRepository.save(user);
        logger.info("Exiting registerUser()");
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Entering getAllUsers()");
        List<User> users = userRepository.findAll();
        logger.info("Exiting getAllUsers()");
        return users;
    }

    @Override
    public User getUserById(int userId) throws UserNotFoundException {
        logger.info("Entering getUserById() with id: {}", userId);
        User user = userRepository.findById(userId);
        if (user == null) {
            logger.error("User with ID {} not found", userId);
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        logger.info("Exiting getUserById()");
        return user;
    }

    @Override
    public User updateUserByuserId(User newUser, int userId) throws UserNotFoundException {
        logger.info("Entering updateUserByuserId() with id: {}", userId);
        if (!userRepository.existsById(userId)) {
            logger.error("User with ID {} not found", userId);
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        newUser.setUserId(userId);
        User updatedUser = userRepository.update(newUser);
        logger.info("Exiting updateUserByuserId()");
        return updatedUser;
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        logger.info("Entering deleteUser() with id: {}", userId);
        if (!userRepository.existsById(userId)) {
            logger.error("User with ID {} not found", userId);
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        userRepository.deleteById(userId);
        logger.info("Exiting deleteUser()");
    }
}
