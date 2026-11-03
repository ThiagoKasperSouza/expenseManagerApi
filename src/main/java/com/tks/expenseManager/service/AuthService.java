package com.tks.expenseManager.service;

import org.springframework.stereotype.Service;

import com.tks.expenseManager.repository.UserRepository;
import com.tks.expenseManager.repository.entities.Role;
import com.tks.expenseManager.repository.entities.User;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already in use");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
