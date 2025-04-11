package com.example.expense_manager.DTO;

//ReminderRequest.java
//package com.example.expense_manager.DTO;

import java.util.List;

public class ReminderRequest {
 private String expenseName;
 private List<ReminderUser> users;

 // Getters and setters
 public String getExpenseName() {
     return expenseName;
 }
 public void setExpenseName(String expenseName) {
     this.expenseName = expenseName;
 }
 public List<ReminderUser> getUsers() {
     return users;
 }
 public void setUsers(List<ReminderUser> users) {
     this.users = users;
 }
}

