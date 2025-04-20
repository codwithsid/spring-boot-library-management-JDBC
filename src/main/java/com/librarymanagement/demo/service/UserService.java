package com.librarymanagement.demo.service;


import com.librarymanagement.demo.exception.userException.UserNotFoundException;
import com.librarymanagement.demo.model.User;
import java.util.List;

public interface UserService {
    void registerUser(User user);
    User getUserById(int userId) throws UserNotFoundException;
    List<User> getAllUsers();
    User updateUserByuserId(User newUser,int userId) throws UserNotFoundException;
    void deleteUser(int userId);
}
