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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

import com.tks.expenseManager.service.ExpenseService;
import com.tks.expenseManager.dtos.ExpenseResponse;
import com.tks.expenseManager.dtos.ExpenseRequest;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping 
    public ExpenseResponse create(
            @RequestBody ExpenseRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        var createdExpense = expenseService.create(request.toExpense(), userDetails.getUsername());
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
    public List<ExpenseResponse> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return expenseService.findAll(userDetails.getUsername()).stream()
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
    public ExpenseResponse findById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        var expense = expenseService.findById(id, userDetails.getUsername());
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
    public ExpenseResponse update(
            @PathVariable Long id,
            @RequestBody ExpenseRequest updatedExpense,
            @AuthenticationPrincipal UserDetails userDetails) {
        var expense = expenseService.update(id, updatedExpense.toExpense(), userDetails.getUsername());
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
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        expenseService.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}
