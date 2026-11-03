package com.tks.expenseManager.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.tks.expenseManager.repository.entities.ExpenseType;
import com.tks.expenseManager.repository.entities.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity 
@Table (name="expenses")
@Getter
@Setter
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private ExpenseType type;

    @Column (nullable = false)
    private PaymentMethod paymentMethod;

    @Column (nullable = false)
    private String description;

    @Column (nullable = false)
    private BigDecimal value;

    @Column (nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column (nullable = false, name = "due_date")
    private LocalDate dueDate;

    @Column (nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

}
