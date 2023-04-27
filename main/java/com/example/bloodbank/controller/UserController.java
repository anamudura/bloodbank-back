package com.example.bloodbank.controller;

import com.example.bloodbank.appuser.Appointment;
import com.example.bloodbank.appuser.Locations;
import com.example.bloodbank.appuser.Users;
import com.example.bloodbank.dto.AppointmentDto;
import com.example.bloodbank.dto.UserRegDto;
import com.example.bloodbank.repo.AppointmentRepository;
import com.example.bloodbank.repo.RoleRepository;
import com.example.bloodbank.repo.UserRepository;
import com.example.bloodbank.service.AppointmentService;
import com.example.bloodbank.service.LocationService;
import com.example.bloodbank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//Desparte controller dupa fiecare actiune
@RestController
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final LocationService locationService;
    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;

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

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody UserRegDto userDto,
                                          BindingResult result) {
        Users existingUser = userService.getUser(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
            return (ResponseEntity<?>) ResponseEntity.internalServerError();
        }

        userService.save(userDto);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> doctors() {
        List<Users> doctors = userService.getDoctors2();
        return ResponseEntity.ok().body(userService.getDoctors2());

    }

    @PostMapping("/users/{id}")
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
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, UserRegDto user) {

        userService.updateDoctor(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/editdon/{id}")
    public ResponseEntity<?> updateDon(@PathVariable("id") Long id, UserRegDto user) {

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

    @GetMapping("/don/{id}")
    public ResponseEntity<Optional<Users>> currentuser(@PathVariable("id") Long id) {
        Optional<Users> user = userService.getDocByid(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/donator/{id}")
    public ResponseEntity<Optional<Users>> currentUserDoc(@PathVariable("id") Long id) {
        Optional<Users> user = userService.getDocByid(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Locations>> getAllLoc() {
        List<Locations> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);

    }

    @PostMapping("/appointment")
    public ResponseEntity<?> appointment(@RequestBody AppointmentDto appointmentDto,
                                         BindingResult result) {
        appointmentService.save(appointmentDto);
        return ResponseEntity.ok(appointmentDto);
    }

    @GetMapping("/getapp/{data}/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentToday(@PathVariable("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
                                                                 @PathVariable("id") Long id) {

        Locations loc = userService.getLocationForUser(id);
        List<Appointment> app = appointmentService.getappDoc(data, loc.getId(), id);
        return ResponseEntity.ok(app);
    }

    @GetMapping("/allapp")
    public ResponseEntity<List<Appointment>> getAllApp(@RequestParam("id")Long id, @RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Locations loc = userService.getLocationForUser(id);
        List<Appointment> app = appointmentRepository.findByIdAndLocations_Id(loc.getId(),id,pageable);
        return ResponseEntity.ok(app);
    }

}
