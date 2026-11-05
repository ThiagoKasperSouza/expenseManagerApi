package com.tks.expenseManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tks.expenseManager.repository.entities.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUser_Email(String email);
    Optional<Expense> findByIdAndUser_Email(Long id, String email);
}
