package com.tks.expenseManager.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.tks.expenseManager.repository.entities.Expense;
import com.tks.expenseManager.repository.entities.ExpenseType;
import com.tks.expenseManager.repository.entities.PaymentMethod;

public record ExpenseRequest(
        String description,
        BigDecimal value,
        @JsonAlias("date") LocalDate dueDate,
        PaymentMethod paymentMethod,
        @JsonAlias("expenseType") ExpenseType type) {

    public Expense toExpense() {
        var expense = new Expense();
        expense.setDescription(this.description());
        expense.setValue(this.value());
        expense.setDueDate(this.dueDate());
        expense.setPaymentMethod(this.paymentMethod());
        expense.setType(this.type());
        return expense;
    }
}
