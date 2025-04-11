package com.example.expense_manager.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.expense_manager.Entity.Expense;
import com.example.expense_manager.Entity.Split;

import jakarta.transaction.Transactional;

public interface SplitRepository extends JpaRepository<Split, Long> {
    
    // Custom method to find all splits related to a specific expense
    List<Split> findByExpense(Expense expense);
    @Transactional
    @Modifying
    @Query("DELETE FROM Split s WHERE s.expense.group.id = :groupId")
    void deleteSplitsByGroupId(@Param("groupId") Long groupId);
    
//    @Transactional
//    @Modifying
//    @Query("UPDATE Split s SET s.settled = true WHERE s.expense.id = :expenseId AND s.user.id = :userId")
//    void settleExpenseForUser(@Param("expenseId") Long expenseId, @Param("userId") Long userId);
//    @Query("UPDATE Split s SET s.settled = true WHERE s.expense.id = :expenseId AND s.user.id = :userId")
//    void settleExpenseForUser(Long expenseId, Long userId);
    @Transactional
    @Modifying
    @Query("UPDATE Split s SET s.isSettled = true WHERE s.expense.id = :expenseId AND s.user.id = :userId")

    void settleExpenseForUser(@Param("expenseId") Long expenseId, @Param("userId") Long userId);


}
