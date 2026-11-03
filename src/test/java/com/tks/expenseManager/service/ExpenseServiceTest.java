package com.tks.expenseManager.service;

import com.tks.expenseManager.repository.ExpenseRepository;
import com.tks.expenseManager.repository.entities.Expense;
import com.tks.expenseManager.repository.entities.ExpenseType;
import com.tks.expenseManager.repository.entities.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private Expense expense;

    @BeforeEach
    void setUp() {
        expense = new Expense();
        expense.setId(1L);
        expense.setDescription("Almoço");
        expense.setValue(new BigDecimal("50.00"));
        expense.setType(ExpenseType.VARIABLE);
        expense.setPaymentMethod(PaymentMethod.PIX);
        expense.setDueDate(LocalDate.now());
        expense.setCreatedAt(LocalDateTime.now());
        expense.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void findAll_shouldReturnList() {
        when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense));
        List<Expense> result = expenseService.findAll();
        assertEquals(1, result.size());
        assertEquals("Almoço", result.get(0).getDescription());
    }

    @Test
    void findById_shouldReturnExpense() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        Expense result = expenseService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_shouldThrowWhenNotFound() {
        when(expenseRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> expenseService.findById(99L));
    }

    @Test
    void update_shouldModifyExpense() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        Expense updated = new Expense();
        updated.setValue(new BigDecimal("100.00"));
        updated.setDescription("Jantar");
        updated.setType(ExpenseType.FIXED);
        updated.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        updated.setDueDate(LocalDate.now().plusDays(1));

        Expense result = expenseService.update(1L, updated);
        assertEquals(new BigDecimal("100.00"), result.getValue());
        assertEquals("Jantar", result.getDescription());
    }

    @Test
    void delete_shouldCallRepository() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        expenseService.delete(1L);
        verify(expenseRepository, times(1)).delete(expense);
    }
}
