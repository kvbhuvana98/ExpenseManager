package com.example.expense_manager.DTO;

public class NotifyRequest {
    private Long toUserId;
    private Long expenseId; // optional, for logging if needed
	public Long getToUserId() {
		return toUserId;
	}
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	public Long getExpenseId() {
		return expenseId;
	}
	
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

    
    
    // Getters and Setters
}