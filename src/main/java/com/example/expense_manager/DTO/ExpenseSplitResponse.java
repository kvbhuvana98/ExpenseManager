package com.example.expense_manager.DTO;

//package DTO;

import java.util.Map;

public class ExpenseSplitResponse {
    private double totalAmount;
    private Map<String, Double> split;

    public ExpenseSplitResponse(double totalAmount, Map<String, Double> split) {
        this.totalAmount = totalAmount;
        this.split = split;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Map<String, Double> getSplit() {
        return split;
    }

    public void setSplit(Map<String, Double> split) {
        this.split = split;
    }

	public ExpenseSplitResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ExpenseSplitResponse [totalAmount=" + totalAmount + ", split=" + split + "]";
	}
	
    
}
