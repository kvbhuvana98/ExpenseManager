package com.example.expense_manager.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expense_manager.Entity.User;

public interface UserRepository extends JpaRepository<com.example.expense_manager.Entity.User, Long> {
	
	
	
//	List<User> findByGroup_Id(Long groupId);
	
}

