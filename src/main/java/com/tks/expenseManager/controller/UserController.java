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
import com.tks.expenseManager.dtos.UserRequest;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping 
    public List<UserResponse> findAll() {
        return userService.findAll().stream().map(e -> new UserResponse(e.getId(), e.getName(), e.getEmail())).toList();
    }

    @GetMapping ("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        User user = userService.findById(id);

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @PutMapping ("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest updatedUser) {
        User user = userService.update(id, updatedUser.toUser());
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
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
