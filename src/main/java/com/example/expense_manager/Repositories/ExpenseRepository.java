package com.example.expense_manager.Repositories;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.expense_manager.Entity.Expense;
import com.example.expense_manager.Entity.Group;

import jakarta.transaction.Transactional;


public interface  ExpenseRepository extends JpaRepository<com.example.expense_manager.Entity.Expense, Long> {
	
	
	
	  @Transactional
	    @Modifying
	    @Query("DELETE FROM Expense e WHERE e.group.id = :groupId")
	    void deleteByGroupId(@Param("groupId") Long groupId);
	  
	  List<Expense> findByGroup_Id(Long groupId); 
	  Optional<Expense> findByGroup_NameAndDescription(String groupName, String description);
	  List<Expense> findByGroup(Group group);

}


