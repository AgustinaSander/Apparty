package com.example.apparty.persistence.repos;

import com.example.apparty.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();
    User getUserById(int id);
    void insertUser(User user);
    void deleteUser(User user);
    User getUserByEmailByPassword(String email, String password);

    List<User> userWithEmailExists(String email);
}
