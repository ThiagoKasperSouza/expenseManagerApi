package com.tks.expenseManager.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponse(Long id, String description, BigDecimal value, LocalDate date, String paymentMethod, String expenseType) {
    
}
