package com.example.expense_manager.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

//Group.java
@Entity
@Table(name = "expense-groups")
public class Group {
 @Id @GeneratedValue
 private Long id;
 private String name;

 @ManyToMany
 private List<User> members;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public List<User> getMembers() {
	return members;
}

public void setMembers(List<User> members) {
	this.members = members;
}

public Group() {
	super();
	// TODO Auto-generated constructor stub
}

public Group(Long id, String name, List<User> members) {
	super();
	this.id = id;
	this.name = name;
	this.members = members;
}

@Override
public String toString() {
	return "Group [id=" + id + ", name=" + name + ", members=" + members + "]";
}
 
 
}

