package com.example.expense_manager.DTO;

public class ExpenseSplitDTO {
    private Long expenseId;
    private String from; // paidBy
    private String to;   // user
    private double amount;
    private boolean settled;

    public ExpenseSplitDTO(Long expenseId, String from, String to, double amount,boolean settled) {
        this.expenseId = expenseId;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.settled = settled;
        
    }

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isSettled() {
		return settled;
	}

	public void setSettled(boolean settled) {
		this.settled = settled;
	}

	
	

    // Getters & setters
    
}

