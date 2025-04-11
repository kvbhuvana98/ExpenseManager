


package com.example.expense_manager.Controller;

import com.example.expense_manager.Services.EmailService;
import com.example.expense_manager.DTO.NotifyRequest;
import com.example.expense_manager.DTO.ReminderRequest;
import com.example.expense_manager.DTO.ReminderUser;
import com.example.expense_manager.Entity.User;
//import com.example.expense_manager.Repositories.UserRepo;
import com.example.expense_manager.Repositories.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")



@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService; // Assuming you already have this

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotifyRequest request) {
        Optional<User> userOpt = userRepo.findById(request.getToUserId());
        
        if (userOpt.isPresent()) {
            User toUser = userOpt.get();
            String email = toUser.getEmail();
            String subject = "Expense Reminder";
            String message = "Hi " + toUser.getName() + ", you have a pending expense to settle.";
            emailService.sendReminder(email, subject, message);
            return ResponseEntity.ok("Notification sent to " + email);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}

































