package com.example.expense_manager.Repositories;



//import com.example.expense_manager.Entity.Split;
import com.example.expense_manager.Entity.Split1;
//import com.example.expense_manager.Entity.Expense;
import com.example.expense_manager.Entity.Expense1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SplitRepository1 extends JpaRepository<Split1, Long> {
   
    List<Split1> findByExpense(Expense1 expense);

}



