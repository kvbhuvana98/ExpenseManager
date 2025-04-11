package com.example.expense_manager.Repositories;


//import com.example.expense_manager.Entity.Expense;
import com.example.expense_manager.Entity.Expense1;
import com.example.expense_manager.Entity.Group;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository1 extends JpaRepository<Expense1, Long> {
	List<Expense1> findByGroup(Group group);

}

