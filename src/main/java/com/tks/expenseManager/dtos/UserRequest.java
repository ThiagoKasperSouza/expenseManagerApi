package com.tks.expenseManager.dtos;

import com.tks.expenseManager.repository.entities.User;

public record UserRequest(Long id, String name, String email) {
    public User toUser() {
        var user = new User();
        user.setId(this.id());
        user.setName(this.name());
        user.setEmail(this.email());
        return user;
    }
}
