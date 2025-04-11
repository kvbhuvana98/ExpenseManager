package com.example.expense_manager.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.expense_manager.Entity.Group;


public interface GroupRepository extends JpaRepository<com.example.expense_manager.Entity.Group, Long> {

	@Modifying
	@Query(value = "DELETE FROM `expense-groups_members` WHERE group_id = :groupId", nativeQuery = true)
	void deleteGroupContentLinks(@Param("groupId") Long groupId);
}

