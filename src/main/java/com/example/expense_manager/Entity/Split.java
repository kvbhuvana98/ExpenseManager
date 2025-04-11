package com.example.expense_manager.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

//Split.java
@Entity
public class Split {
 @Id @GeneratedValue
 private Long id;

 @ManyToOne
 private Expense expense;

 @ManyToOne
 private User user;

 private double amountOwed;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Expense getExpense() {
	return expense;
}

public void setExpense(Expense expense) {
	this.expense = expense;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public double getAmountOwed() {
	return amountOwed;
}

public void setAmountOwed(double amountOwed) {
	this.amountOwed = amountOwed;
}

public Split() {
	super();
	// TODO Auto-generated constructor stub
}

private Boolean isSettled;  // New field to track settlement status

// Getters and setters

public Boolean isSettled() {
    return isSettled;
}

public void setSettled(Boolean isSettled) {
    this.isSettled = isSettled;
}


public Split(Long id, Expense expense, User user, double amountOwed) {
	super();
	this.id = id;
	this.expense = expense;
	this.user = user;
	this.amountOwed = amountOwed;
}

@Override
public String toString() {
	return "Split [id=" + id + ", expense=" + expense + ", user=" + user + ", amountOwed=" + amountOwed + "]";
}
 
}

