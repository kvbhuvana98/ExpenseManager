package com.example.expense_manager.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expense_manager.Entity.Expense;
import com.example.expense_manager.Entity.Split;
import com.example.expense_manager.Entity.User;
import com.example.expense_manager.Repositories.ExpenseRepository;
import com.example.expense_manager.Repositories.SplitRepository;
import com.example.expense_manager.Repositories.UserRepository;

@Service
public class ExpenseService {
    @Autowired private SplitRepository splitRepo;

    public void splitEqually(Expense expense, List<User> users) {
        double amountEach = expense.getAmount() / users.size();

        for (User user : users) {
            Split split = new Split();
            split.setExpense(expense);
            split.setUser(user);
            split.setAmountOwed(amountEach);
            splitRepo.save(split);
        }
    }
    
    
}
	
	
////	@Autowired private UserRepository userRepo;
////	@Autowired private ExpenseRepository expenseRepo;
////
////	public void splitEqually(Long expenseId, List<Long> userIds) {
////	    Expense expense = expenseRepo.findById(expenseId)
////	        .orElseThrow(() -> new RuntimeException("Expense not found"));
////
////	    List<User> users = userRepo.findAllById(userIds);
////
////	    double amountEach = expense.getAmount() / users.size();
////
////	    for (User user : users) {
////	        Split split = new Split();
////	        split.setExpense(expense);
////	        split.setUser(user);
////	        split.setAmountOwed(amountEach);
////	        splitRepo.save(split);
////	    }
////	}
//
//}

//
//package com.example.expense_manager.Services;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.expense_manager.Entity.Expense;
//import com.example.expense_manager.Entity.Group;
//import com.example.expense_manager.Entity.Split;
//import com.example.expense_manager.Entity.User;
//import com.example.expense_manager.Repositories.ExpenseRepository;
//import com.example.expense_manager.Repositories.GroupRepository;
//import com.example.expense_manager.Repositories.SplitRepository;
//
//@Service
//public class ExpenseService {
//
//    @Autowired private GroupRepository groupRepo;
//    @Autowired private SplitRepository splitRepo;
//    @Autowired private ExpenseRepository expenseRepo;
//
//    // Method to split the amount equally among the members of the group
//    public void splitExpenseAmongGroup(Long groupId, double amount) {
//        // Fetch the group by its ID
//        Group group = groupRepo.findById(groupId)
//                .orElseThrow(() -> new RuntimeException("Group not found"));
//
//        // Get the members of the group
//        List<User> members = group.getMembers();
//        
//        // Ensure there are members in the group
//        if (members.isEmpty()) {
//            throw new RuntimeException("No members in the group to split the expense");
//        }
//
//        // Calculate the amount each member needs to pay
//        double amountEach = amount / members.size();
//
//        // Create an expense record
//        Expense expense = new Expense();
//        expense.setDescription("Group expense split");
//        expense.setAmount(amount);
//        expense.setGroup(group);
//        // Set the expense as saved (optional, depending on how you want to handle it)
//        expenseRepo.save(expense);
//
//        // Split the amount equally among all members and save the splits
//        for (User user : members) {
//            Split split = new Split();
//            split.setExpense(expense);
//            split.setUser(user);
//            split.setAmountOwed(amountEach);
//            splitRepo.save(split);
//        }
//    }
//}
//
