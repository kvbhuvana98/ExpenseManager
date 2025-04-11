package com.example.expense_manager.DTO;


public class ExpenseDTO {
    private Long id;
    private String description;

    public ExpenseDTO(Long id, String description) {
        this.id = id;
        this.description = description;
    }
    

    
    

    public void setId(Long id) {
		this.id = id;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	// Getters and setters
    public Long getId() { return id; }
    public String getDescription() { return description; }
}

