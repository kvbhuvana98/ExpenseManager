package com.example.expense_manager.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

//Expense.java
@Entity
public class Expense {
 @Id @GeneratedValue
 private Long id;
 private String description;
 private double amount;

 @ManyToOne

@JoinColumn(name = "group_id")
 private Group group;

 @ManyToOne
 private User paidBy;

 private LocalDateTime dateTime;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public double getAmount() {
	return amount;
}

public void setAmount(double amount) {
	this.amount = amount;
}

public Group getGroup() {
	return group;
}

public void setGroup(Group group) {
	this.group = group;
}

public User getPaidBy() {
	return paidBy;
}

public void setPaidBy(User paidBy) {
	this.paidBy = paidBy;
}

public LocalDateTime getDateTime() {
	return dateTime;
}

public void setDateTime(LocalDateTime dateTime) {
	this.dateTime = dateTime;
}


@OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
private List<Split> splits;

public List<Split> getSplits() {
    return splits;
}


public Expense() {
	super();
	// TODO Auto-generated constructor stub
}

public Expense(Long id, String description, double amount, Group group, User paidBy, LocalDateTime dateTime) {
	super();
	this.id = id;
	this.description = description;
	this.amount = amount;
	this.group = group;
	this.paidBy = paidBy;
	this.dateTime = dateTime;
}

@Override
public String toString() {
	return "Expense [id=" + id + ", description=" + description + ", amount=" + amount + ", group=" + group
			+ ", paidBy=" + paidBy + ", dateTime=" + dateTime + "]";
}
 
}

