package com.example.expense_manager.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense_manager.DTO.ExpenseDTO;
import com.example.expense_manager.DTO.ExpenseSplitDTO;
import com.example.expense_manager.DTO.ExpenseSplitResponse;
import com.example.expense_manager.DTO.UserDTO;
//import com.example.expense_manager.DTO.SplitRequest;
import com.example.expense_manager.Entity.Expense;
import com.example.expense_manager.Entity.Expense1;
import com.example.expense_manager.Entity.Group;
import com.example.expense_manager.Entity.Split;
import com.example.expense_manager.Entity.Split1;
import com.example.expense_manager.Entity.User;
import com.example.expense_manager.Repositories.ExpenseRepository;
import com.example.expense_manager.Repositories.ExpenseRepository1;
import com.example.expense_manager.Repositories.GroupRepository;
import com.example.expense_manager.Repositories.SplitRepository;
import com.example.expense_manager.Repositories.SplitRepository1;
import com.example.expense_manager.Repositories.UserRepository;
import com.example.expense_manager.Services.ExpenseService;

import jakarta.transaction.Transactional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class GroupController {

    @Autowired private GroupRepository groupRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private ExpenseRepository expenseRepo;
    @Autowired private ExpenseService expenseService;
    @Autowired private SplitRepository splitRepo; 
   
   

    @GetMapping("/groups")
    public List<Group> getAllGroups() {
        return groupRepo.findAll();
    }
    
    @GetMapping("/groups/{groupId}/members")
    public List<User> getGroupMembers(@PathVariable Long groupId) {
        Group group = groupRepo.findById(groupId).orElse(null);
        if (group != null) {
            return group.getMembers();
        } else {
            return new ArrayList<>();
        }
    }
    
    @GetMapping("/groups/{groupId}")
    public Group getGroupById(@PathVariable Long groupId) {
        return groupRepo.findById(groupId).orElse(null);
    }
    @PostMapping("/groups")
    public Group createGroup(@RequestBody Group group) {
        return groupRepo.save(group);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepo.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/groups/{groupId}/addUser")
    public Group addUserToGroup(@PathVariable Long groupId, @RequestBody User user) {
        Group group = groupRepo.findById(groupId).get();
        userRepo.save(user);
        group.getMembers().add(user);
        return groupRepo.save(group);
    }
    
    
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        return userRepo.findById(userId)
            .map(user -> {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                User savedUser = userRepo.save(user);
                return ResponseEntity.ok(savedUser);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }



    

    @Transactional
    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {

        // Step 1: Remove entries from join table
        groupRepo.deleteGroupContentLinks(groupId);

        // Step 2: Delete related entities
        splitRepo.deleteSplitsByGroupId(groupId); 
        expenseRepo.deleteByGroupId(groupId);

        // Step 3: Delete group itself
        groupRepo.deleteById(groupId);

        return ResponseEntity.ok("Group deleted successfully!");
    }
    
    @GetMapping("/expenses/{expenseId}/paid-details")
    public ResponseEntity<?> getPaidDetails(@PathVariable Long expenseId) {
        Expense expense = expenseRepo.findById(expenseId).orElse(null);
        if (expense == null) return ResponseEntity.notFound().build();

        Map<String, Object> response = new HashMap<>();
        response.put("expenseId", expense.getId());
        response.put("totalAmount", expense.getAmount());
        response.put("paidBy", expense.getPaidBy().getName());

        List<Split> splits = splitRepo.findByExpense(expense);
        List<Map<String, Object>> borrowers = new ArrayList<>();

        for (Split s : splits) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("user", s.getUser().getName());
            userInfo.put("owed", s.getAmountOwed());
            userInfo.put("isSettled", s.isSettled()); // you may need to add this field
            borrowers.add(userInfo);
        }

        response.put("borrowers", borrowers);

        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/expenses/{expenseId}/settle/{userId}")
    public ResponseEntity<String> settleExpense1(@PathVariable Long expenseId, @PathVariable Long userId) {
        // Call the repository method to settle the split for the user
        try {
            splitRepo.settleExpenseForUser(expenseId, userId);  // Use expenseId and userId
            return ResponseEntity.ok("Expense settled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while settling expense: " + e.getMessage());
        }
    }
    @GetMapping("/expenses/{expenseId}/isSettled")
    public ResponseEntity<Boolean> isExpenseSettled(@PathVariable Long expenseId) {
        Optional<Expense> optionalExpense = expenseRepo.findById(expenseId);

        if (optionalExpense.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Expense expense = optionalExpense.get();
        List<Split> splits = expense.getSplits();

        if (splits == null || splits.isEmpty()) {
            // No splits => maybe treat as settled or unsettled? Customize this logic
            return ResponseEntity.ok(false);
        }

        // Defensive check: log nulls and prevent NPE
        boolean allSettled = splits.stream()
            .filter(Objects::nonNull)
            .peek(split -> {
                if (split.isSettled() == null) {
                    System.err.println("Null isSettled in split: " + split.getId());
                }
            })
            .allMatch(split -> Boolean.TRUE.equals(split.isSettled()));

        return ResponseEntity.ok(allSettled);
    }


    @DeleteMapping("/groups/{groupId}/users/{userName}")
    public ResponseEntity<String> removeUserFromGroup(@PathVariable Long groupId, @PathVariable String userName) {
        Group group = groupRepo.findById(groupId).orElseThrow();
        group.getMembers().removeIf(user -> user.getName().equalsIgnoreCase(userName));
        groupRepo.save(group);
        return ResponseEntity.ok("User removed from group");
    }
    
    @PostMapping("/groups/{groupId}/users")
    public ResponseEntity<String> addUserToExistingGroup(@PathVariable Long groupId, @RequestBody User newUser) {
        Group group = groupRepo.findById(groupId).orElseThrow();

        // Save the new user first to avoid transient error
        User savedUser = userRepo.save(newUser);

        group.getMembers().add(savedUser);
        groupRepo.save(group);

        return ResponseEntity.ok("User added to group successfully");
    }
    

    
    
    
    @GetMapping("/expenses/{expenseId}/split")
    public ExpenseSplitResponse getExpenseSplit(@PathVariable Long expenseId) {
        Expense expense = expenseRepo.findById(expenseId).orElse(null);
        if (expense == null) return null;

        List<Split> splits = splitRepo.findByExpense(expense);

        Map<String, Double> splitMap = new HashMap<>();
        for (Split s : splits) {
            splitMap.put(s.getUser().getName(), s.getAmountOwed()); // ✅ Correct getter
        }

        return new ExpenseSplitResponse(expense.getAmount(), splitMap);
    }
    
    @PostMapping("/groups/{groupId}/expense")
    public String addExpense(@PathVariable Long groupId, @RequestBody Expense expense) {
        Group group = groupRepo.findById(groupId).get();
        expense.setGroup(group);
        expenseRepo.save(expense);

        List<User> members = group.getMembers();
        expenseService.splitEqually(expense, members);

        return "Expense added and split equally!";
    }
    
    
    @GetMapping("/groups/{groupId}/expenses")
    public List<ExpenseDTO> getExpensesByGroup(@PathVariable Long groupId) {
        return expenseRepo.findByGroup_Id(groupId)
                          .stream()
                          .map(exp -> new ExpenseDTO(exp.getId(), exp.getDescription()))
                          .collect(Collectors.toList());
    }
 
    

    
    @GetMapping("/expense-summary")
    public List<ExpenseSplitDTO> getExpenseSplits() {
        List<Split> splits = splitRepo.findAll();
        List<ExpenseSplitDTO> result = new ArrayList<>();

        for (Split split : splits) {
            Expense expense = split.getExpense();
            if (expense != null && expense.getPaidBy() != null) {
                ExpenseSplitDTO dto = new ExpenseSplitDTO(
                    expense.getId(),
                    split.getUser().getName(),                // From (borrower)
                    expense.getPaidBy().getName(),            // To (payer)
                    split.getAmountOwed(),
                    Boolean.TRUE.equals(split.isSettled())    // ✅ Pass settlement status
                );
                result.add(dto);
            }
        }
        return result;
    }


    
    @DeleteMapping("/expenses/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long expenseId) {
        Expense expense = expenseRepo.findById(expenseId).orElse(null);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }

        // Delete all splits first (to avoid constraint errors)
        List<Split> splits = splitRepo.findByExpense(expense);
        splitRepo.deleteAll(splits);

        // Now delete the expense
        expenseRepo.delete(expense);

        return ResponseEntity.ok("Expense deleted successfully!");
    }
    
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
            .map(user -> new UserDTO(user.getId(), user.getName()))
            .collect(Collectors.toList());
    }

    @GetMapping("/expenses")
    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepo.findAll().stream()
            .map(exp -> new ExpenseDTO(exp.getId(), exp.getDescription()))
            .collect(Collectors.toList());
    }
    @GetMapping("/groups/{groupId}/users")
    public List<UserDTO> getUsersByGroup(@PathVariable Long groupId) {
        return userRepo.findById(groupId)
                       .stream()
                       .map(user -> new UserDTO(user.getId(), user.getName()))
                       .collect(Collectors.toList());
    }
    

    
    @GetMapping("/groups/{groupName}/expenses/{description}/split")
    public ResponseEntity<Map<String, Object>> getSplitWithStatusByGroupAndDescription(
            @PathVariable String groupName,
            @PathVariable String description) {

        Optional<Expense> optionalExpense = expenseRepo.findByGroup_NameAndDescription(groupName, description);

        if (optionalExpense.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Expense expense = optionalExpense.get();
        List<Split> splits = splitRepo.findByExpense(expense);

        Map<String, Object> response = new HashMap<>();
        response.put("totalAmount", expense.getAmount());
        response.put("paidBy", expense.getPaidBy().getName());

        Map<String, Map<String, Object>> splitDetails = new HashMap<>();
        boolean allSettled = true;

        for (Split s : splits) {
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("amount", s.getAmountOwed());
            userDetails.put("isSettled", Boolean.TRUE.equals(s.isSettled()));
            if (!Boolean.TRUE.equals(s.isSettled())) {
                allSettled = false;
            }

            splitDetails.put(s.getUser().getName(), userDetails);
        }

        response.put("split", splitDetails);
        response.put("fullySettled", allSettled);

        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/groups/{groupId}/summary")
    public ResponseEntity<Map<String, Double>> getGroupSummary(@PathVariable Long groupId) {
        Group group = groupRepo.findById(groupId).orElse(null);
        if (group == null) return ResponseEntity.notFound().build();

        Map<String, Double> balances = new HashMap<>();

        for (User user : group.getMembers()) {
            balances.put(user.getName(), 0.0);
        }

        List<Expense> groupExpenses = expenseRepo.findByGroup(group);
        for (Expense expense : groupExpenses) {
            for (Split split : splitRepo.findByExpense(expense)) {
                if (Boolean.TRUE.equals(split.isSettled())) continue;

                String borrower = split.getUser().getName();
                User payerUser = expense.getPaidBy();
                if (payerUser == null) {
                    System.out.println("Expense with ID " + expense.getId() + " has no payer assigned!");
                    continue; // Or handle however you want
                }

                String payer = payerUser.getName();
                double amount = split.getAmountOwed();

                balances.put(borrower, balances.getOrDefault(borrower, 0.0) - amount);
                balances.put(payer, balances.getOrDefault(payer, 0.0) + amount);
            }
        }

        return ResponseEntity.ok(balances);
    }











}















































//@PostMapping("/groups/{groupId}/expense")
//public String addExpense(@PathVariable Long groupId, @RequestBody Expense expense) {
//  Group group = groupRepo.findById(groupId).get();
//  expense.setGroup(group);
//  expenseRepo.save(expense);
//
//  List<User> members = group.getMembers();
//  expenseService.splitEqually(expense, members);
//
//  return "Expense added and split equally!";
//}
//
//@PostMapping("/split/{groupId}")
//public String splitExpense(@PathVariable Long groupId, @RequestParam double amount) {
//  try {
//      expenseService.splitExpenseAmongGroup(groupId, amount);
//      return "Expense split successfully!";
//  } catch (RuntimeException e) {
//      return e.getMessage();
//  }
//}
//
//@GetMapping("/groups/{groupId}/expense/{expenseId}/split")
//public ExpenseSplitResponse getExpenseSplit(@PathVariable Long groupId, @PathVariable Long expenseId) {
//  // Retrieve the expense
//  Expense expense = expenseRepo.findById(expenseId).orElse(null);
//  if (expense == null) {
//      throw new RuntimeException("Expense not found!");
//  }
//
//  // Make sure the expense belongs to the specified group
//  if (!expense.getGroup().getId().equals(groupId)) {
//      throw new RuntimeException("Expense does not belong to this group.");
//  }
//
//  // Fetch the splits for the given expense
//  List<Split> splits = splitRepo.findByExpense(expense);
//  Map<String, Double> splitMap = new HashMap<>();
//  for (Split split : splits) {
//      splitMap.put(split.getUser().getName(), split.getAmountOwed());
//  }
//
//  // Return the split details
//  return new ExpenseSplitResponse(expense.getAmount(), splitMap);
//}





//@PostMapping("/expenses/split")
//public ResponseEntity<String> splitExpense(@RequestParam Long groupId, @RequestParam double amount) {
//  Group group = groupRepo.findById(groupId).orElse(null);
//  if (group == null) {
//      return ResponseEntity.badRequest().body("Group not found");
//  }
//
//  List<User> members = group.getMembers();
//  if (members.isEmpty()) {
//      return ResponseEntity.badRequest().body("No members in the group to split the expense.");
//  }
//
//  // Create the expense
//  Expense1 expense = new Expense1(amount, group);
//  expenseRepo.save(expense);
//
//  // Split the expense equally
//  double amountPerMember = amount / members.size();
//  for (User user : members) {
//      Split1 split = new Split1(amountPerMember, user, expense);
//      splitRepo.save(split);
//  }
//
//  return ResponseEntity.ok("Expense split successfully.");
//}
//
//@GetMapping("/groups/{groupId}/split")
//public ResponseEntity<Map<String, Double>> getGroupExpenseSplit(@PathVariable Long groupId) {
//  Group group = groupRepo.findById(groupId).orElse(null);
//  if (group == null) {
//      return ResponseEntity.badRequest().build();
//  }
//
//  List<Expense1> expenses = expenseRepo.findByGroup(group);
//  Map<String, Double> userSplits = new HashMap<>();
//
//  for (Expense1 expense : expenses) {
//      List<Split1> splits = splitRepo.findByExpense(expense);
//      for (Split1 split : splits) {
//          String userName = split.getUser().getName();
//          double amount = split.getAmountOwed();
//          userSplits.put(userName, userSplits.getOrDefault(userName, 0.0) + amount);
//      }
//  }
//
//  return ResponseEntity.ok(userSplits);
//}



//@GetMapping("/expenses/{expenseId}/split")
//
//public ExpenseSplitResponse getExpenseSplit(@PathVariable Long expenseId) {
//	
//  Expense expense = expenseRepo.findById(expenseId).orElse(null);
//  if (expense == null) return null;
//
//  List<Split> splits = splitRepo.findByExpense(expense); // Make sure this method exists in your SplitRepository
//
//  Map<String, Double> splitMap = new HashMap<>();
//  for (Split s : splits) {
//      splitMap.put(s.getUser().getName(), s.getAmountOwed());
//  }
//
//  return new ExpenseSplitResponse(expense.getAmount(), splitMap);
//}
