package com.tks.expenseManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tks.expenseManager.repository.ExpenseRepository;
import com.tks.expenseManager.repository.UserRepository;
import com.tks.expenseManager.repository.entities.Expense;
import com.tks.expenseManager.repository.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public Expense create(Expense expense, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Authenticated user not found"));
        expense.setUser(user);
        expense.setCreatedAt(LocalDateTime.now());
        expense.setUpdatedAt(LocalDateTime.now());
        return expenseRepository.save(expense);
    }

    public List<Expense> findAll(String userEmail) {
        return expenseRepository.findAllByUser_Email(userEmail);
    }

    public Expense findById(Long id, String userEmail) {
        return expenseRepository.findByIdAndUser_Email(id, userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found with id: " + id));
    }

    public Expense update(Long id, Expense updatedExpense, String userEmail) {
        Expense expense = findById(id, userEmail);
        expense.setValue(updatedExpense.getValue());
        expense.setDescription(updatedExpense.getDescription());
        expense.setType(updatedExpense.getType());
        expense.setPaymentMethod(updatedExpense.getPaymentMethod());
        expense.setDueDate(updatedExpense.getDueDate());
        expense.setUpdatedAt(LocalDateTime.now());
        return expenseRepository.save(expense);
    }

    public void delete(Long id, String userEmail) {
        Expense expense = findById(id, userEmail);
        expenseRepository.delete(expense);
    }
}
