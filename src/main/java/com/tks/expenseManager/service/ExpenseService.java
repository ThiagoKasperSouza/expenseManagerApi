package com.tks.expenseManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tks.expenseManager.repository.ExpenseRepository;
import com.tks.expenseManager.repository.entities.Expense;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public Expense findById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found with id: " + id));
    }

    public Expense update(Long id, Expense updatedExpense) {
        Expense expense = findById(id);
        expense.setValue(updatedExpense.getValue());
        expense.setDescription(updatedExpense.getDescription());
        expense.setType(updatedExpense.getType());
        expense.setPaymentMethod(updatedExpense.getPaymentMethod());
        expense.setDueDate(updatedExpense.getDueDate());
        expense.setUpdatedAt(LocalDateTime.now());
        return expenseRepository.save(expense);
    }

    public void delete(Long id) {
        Expense expense = findById(id);
        expenseRepository.delete(expense);
    }
}
