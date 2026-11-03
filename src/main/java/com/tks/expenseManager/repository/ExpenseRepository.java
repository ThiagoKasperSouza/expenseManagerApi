package com.tks.expenseManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tks.expenseManager.repository.entities.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
}
