package com.librarymanagement.demo.service.impl;


import com.librarymanagement.demo.exception.userException.UserNotFoundException;
import com.librarymanagement.demo.model.User;
import com.librarymanagement.demo.repository.UserRepository;
import com.librarymanagement.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }

    @Override
    public User updateUserByuserId(User newUser, int userId) throws UserNotFoundException {
        return userRepository.findById(userId).map(user -> {
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setEmailId(newUser.getEmailId());
            user.setMobileNumber(newUser.getMobileNumber());
            user.setRole(newUser.getRole());
            user.setProfileImageUrl(newUser.getProfileImageUrl());
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }


    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
