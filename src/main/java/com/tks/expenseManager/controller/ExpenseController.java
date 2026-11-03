package com.tks.expenseManager.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

import com.tks.expenseManager.service.ExpenseService;
import com.tks.expenseManager.dtos.ExpenseResponse;
import com.tks.expenseManager.repository.entities.Expense;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping 
    public ExpenseResponse create(@RequestBody Expense expense) {
        var createdExpense = expenseService.create(expense);
        return new ExpenseResponse(
                createdExpense.getId(),
                createdExpense.getDescription(),
                createdExpense.getValue(),
                createdExpense.getDueDate(),
                createdExpense.getPaymentMethod().name(),
                createdExpense.getType().name()
        );
    }

    @GetMapping
    public List<ExpenseResponse> findAll() {
        return expenseService.findAll().stream()
                .map(e -> new ExpenseResponse(
                        e.getId(),
                        e.getDescription(),
                        e.getValue(),
                        e.getDueDate(),
                        e.getPaymentMethod().name(),
                        e.getType().name()
                ))
                .toList();
    }

    @GetMapping ("/{id}")
    public ExpenseResponse findById(@PathVariable Long id) {
        var expense = expenseService.findById(id);
        return new ExpenseResponse(
                expense.getId(),
                expense.getDescription(),
                expense.getValue(),
                expense.getDueDate(),
                expense.getPaymentMethod().name(),
                expense.getType().name()
        );
    }

    @PutMapping ("/{id}")
    public ExpenseResponse update(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        var expense = expenseService.update(id, updatedExpense);
        return new ExpenseResponse(
                expense.getId(),
                expense.getDescription(),
                expense.getValue(),
                expense.getDueDate(),
                expense.getPaymentMethod().name(),
                expense.getType().name()
        );
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
