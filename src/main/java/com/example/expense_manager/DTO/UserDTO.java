package com.example.expense_manager.DTO;

//package com.example.expense_manager.dto;

public class UserDTO {
    private Long id;
    private String name;

    public UserDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    

    public void setId(Long id) {
		this.id = id;
	}



	public void setName(String name) {
		this.name = name;
	}



	// Getters and setters
    public Long getId() { return id; }
    public String getName() { return name; }
}

