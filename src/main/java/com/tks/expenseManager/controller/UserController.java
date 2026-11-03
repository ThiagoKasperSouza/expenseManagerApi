package com.tks.expenseManager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.userdetails.UserDetails;

import com.tks.expenseManager.repository.entities.User;
import com.tks.expenseManager.service.UserService;
import com.tks.expenseManager.dtos.UserResponse;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping 
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping ("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping ("/{id}")
    public User update(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.update(id, updatedUser);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    } 
    
    @GetMapping ("/me")
    public UserResponse me(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
