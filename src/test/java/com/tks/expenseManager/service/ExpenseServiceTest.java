package com.tks.expenseManager.service;

import com.tks.expenseManager.repository.ExpenseRepository;
import com.tks.expenseManager.repository.UserRepository;
import com.tks.expenseManager.repository.entities.Expense;
import com.tks.expenseManager.repository.entities.ExpenseType;
import com.tks.expenseManager.repository.entities.PaymentMethod;
import com.tks.expenseManager.repository.entities.User;
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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private Expense expense;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("owner@example.com");

        expense = new Expense();
        expense.setId(1L);
        expense.setDescription("Almoço");
        expense.setValue(new BigDecimal("50.00"));
        expense.setType(ExpenseType.VARIABLE);
        expense.setPaymentMethod(PaymentMethod.PIX);
        expense.setDueDate(LocalDate.now());
        expense.setCreatedAt(LocalDateTime.now());
        expense.setUpdatedAt(LocalDateTime.now());
        expense.setUser(user);
    }

    @Test
    void create_shouldAssignAuthenticatedUser() {
        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(user));
        when(expenseRepository.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Expense newExpense = new Expense();

        Expense result = expenseService.create(newExpense, "owner@example.com");

        assertSame(user, result.getUser());
        verify(expenseRepository).save(newExpense);
    }

    @Test
    void findAll_shouldReturnOnlyAuthenticatedUsersExpenses() {
        when(expenseRepository.findAllByUser_Email("owner@example.com")).thenReturn(Arrays.asList(expense));
        List<Expense> result = expenseService.findAll("owner@example.com");

        assertEquals(1, result.size());
        assertEquals("Almoço", result.get(0).getDescription());
        verify(expenseRepository).findAllByUser_Email("owner@example.com");
        verify(expenseRepository, never()).findAll();
    }

    @Test
    void findById_shouldReturnExpenseOwnedByAuthenticatedUser() {
        when(expenseRepository.findByIdAndUser_Email(1L, "owner@example.com")).thenReturn(Optional.of(expense));
        Expense result = expenseService.findById(1L, "owner@example.com");

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_shouldHideExpensesOwnedByAnotherUser() {
        when(expenseRepository.findByIdAndUser_Email(1L, "other@example.com")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> expenseService.findById(1L, "other@example.com"));

        assertEquals(404, exception.getStatusCode().value());
    }

    @Test
    void update_shouldModifyExpense() {
        when(expenseRepository.findByIdAndUser_Email(1L, "owner@example.com")).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        Expense updated = new Expense();
        updated.setValue(new BigDecimal("100.00"));
        updated.setDescription("Jantar");
        updated.setType(ExpenseType.FIXED);
        updated.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        updated.setDueDate(LocalDate.now().plusDays(1));

        Expense result = expenseService.update(1L, updated, "owner@example.com");
        assertEquals(new BigDecimal("100.00"), result.getValue());
        assertEquals("Jantar", result.getDescription());
        assertSame(user, result.getUser());
    }

    @Test
    void delete_shouldCallRepository() {
        when(expenseRepository.findByIdAndUser_Email(1L, "owner@example.com")).thenReturn(Optional.of(expense));
        expenseService.delete(1L, "owner@example.com");

        verify(expenseRepository, times(1)).delete(expense);
    }
}
