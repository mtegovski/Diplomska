package com.example.diplomska.service.impl;

import com.example.diplomska.model.Role;
import com.example.diplomska.model.User;
import com.example.diplomska.repository.UserRepository;
import com.example.diplomska.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    @Transactional
    public void updateUserRole(String email, Role role) {
        this.userRepository.updateUserRole(email, role);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }


}
