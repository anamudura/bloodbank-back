package com.example.bloodbank.controller;

import com.example.bloodbank.entity.Users;
import com.example.bloodbank.dto.UserRegDto;
import com.example.bloodbank.repo.RoleRepository;
import com.example.bloodbank.repo.UserRepository;
import com.example.bloodbank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @GetMapping("/doctors")
    public ResponseEntity<List<Users>> doctors() {
        List<Users> doctors = userService.getDoctors2();
        return ResponseEntity.ok().body(userService.getDoctors2());

    }
    @PostMapping("/doctors/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {

        roleRepository.deleteRole(id);
        userRepository.deleteUser(id);
        return ResponseEntity.ok("");
    }

    @GetMapping("/edituser/{id}")
    public ResponseEntity<?> showUpdateForm(@PathVariable("id") Long id) {
        Optional<Users> user = userService.getDocByid(id);
        if (user.isEmpty())
            throw new UsernameNotFoundException("Aoleo");
        return ResponseEntity.ok(user);

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> showUser(@PathVariable("id") Long id) {
        Optional<Users> user = userService.getDocByid(id);
        if (user.isEmpty())
            throw new UsernameNotFoundException("Aoleo");
        return ResponseEntity.ok(user);

    }

    @PostMapping("/edituser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserRegDto user) {

        System.out.println(user.getEmail());
        System.out.println(user.getNume());
        System.out.println(user.getLocation());
        user.setId(id);
        userService.updateDoctor(user);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/registerdoc")
    public ResponseEntity<?> registrationDoc(@RequestBody UserRegDto userDto,
                                             BindingResult result) {
        Users existingUser = userService.getUser(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
            return (ResponseEntity<?>) ResponseEntity.internalServerError();
        }

        userService.saveDoc(userDto);
        return ResponseEntity.ok(userDto);
    }
}
