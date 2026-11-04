package com.tks.expenseManager.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.tks.expenseManager.repository.entities.Expense;
import com.tks.expenseManager.repository.entities.ExpenseType;
import com.tks.expenseManager.repository.entities.PaymentMethod;

public record ExpenseRequest(Long id, String description, BigDecimal value, LocalDate date, String paymentMethod, String expenseType) {
    public Expense toExpense() {
        var expense = new Expense();
        expense.setId(this.id());
        expense.setDescription(this.description());
        expense.setValue(this.value());
        expense.setDueDate(this.date());
        expense.setPaymentMethod(PaymentMethod.valueOf(this.paymentMethod()));
        expense.setType(ExpenseType.valueOf(this.expenseType()));
        return expense;
    }
}

