package com.example.bloodbank.controller;

import com.example.bloodbank.entity.Users;
import com.example.bloodbank.dto.UserRegDto;
import com.example.bloodbank.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin("https://localhost:3000")
@RequestMapping("")
public class MainController {
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRegDto user) {
        Users user1 = userRepository.findByEmail(user.getEmail());
        System.out.println("Am intrat in metoda asta");
        if (user1.getPassword().equals(user.getPassword())) {
            System.out.println("LOGIN SUCCESSFUL");
            return ResponseEntity.ok(user1);
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();

    }

}
