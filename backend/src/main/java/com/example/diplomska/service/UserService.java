package com.example.diplomska.service;

import com.example.diplomska.model.Role;
import com.example.diplomska.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    void updateUserRole(String email, Role role);
    List<User> findAll();

}
