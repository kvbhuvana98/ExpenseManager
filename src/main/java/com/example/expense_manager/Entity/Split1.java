package com.example.expense_manager.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Split1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amountOwed;

    @ManyToOne
    @JoinColumn(name = "user_id")  // Foreign key column name
    private User user;

    @ManyToOne
    @JoinColumn(name = "expense_id")  // Foreign key column name
    private Expense1 expense;

    // Default constructor
    public Split1() {
    }

    // Constructor with fields
    public Split1(double amountOwed, User user, Expense1 expense) {
        this.amountOwed = amountOwed;
        this.user = user;
        this.expense = expense;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Expense1 getExpense() {
        return expense;
    }

    public void setExpense(Expense1 expense) {
        this.expense = expense;
    }

    // Optional: toString for debugging
    @Override
    public String toString() {
        return "Split1{" +
                "id=" + id +
                ", amountOwed=" + amountOwed +
                ", user=" + (user != null ? user.getId() : null) +
                ", expense=" + (expense != null ? expense.getId() : null) +
                '}';
    }
}
