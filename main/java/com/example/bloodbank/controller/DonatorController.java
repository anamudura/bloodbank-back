package com.example.bloodbank.controller;

import com.example.bloodbank.entity.Locations;
import com.example.bloodbank.entity.Users;
import com.example.bloodbank.dto.AppointmentDto;
import com.example.bloodbank.dto.UserRegDto;
import com.example.bloodbank.pdf.PdfGenerator;
import com.example.bloodbank.repo.AppointmentRepository;
import com.example.bloodbank.repo.LocationsRepository;
import com.example.bloodbank.repo.RoleRepository;
import com.example.bloodbank.repo.UserRepository;
import com.example.bloodbank.service.AppointmentService;
import com.example.bloodbank.service.LocationService;
import com.example.bloodbank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
@RequestMapping("/donator")
public class DonatorController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final LocationService locationService;
    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;
    private final LocationsRepository locationsRepository;
    private final PdfGenerator pdfGenerator;


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

    @PostMapping("/editdon/{id}")
    public ResponseEntity<?> updateDon(@PathVariable("id") Long id, @RequestBody UserRegDto user) {


        user.setId(id);
        userService.updateDoctor(user);
        return ResponseEntity.ok(user);
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

    @PostMapping("/appointment/{id1}/{id2}")
    public ResponseEntity<?> appointment(@RequestBody AppointmentDto appointmentDto,
                                         @PathVariable("id1") Long id1,
                                         @PathVariable("id2") Long id2,
                                         BindingResult result) {
        appointmentService.save(appointmentDto, id1,id2);
        return ResponseEntity.ok(appointmentDto);
    }
    @GetMapping("/edituser/{id}")
    public ResponseEntity<?> showUpdateForm(@PathVariable("id") Long id) {
        Optional<Users> user = userService.getDocByid(id);
        if (user.isEmpty())
            throw new UsernameNotFoundException("Aoleo");
        return ResponseEntity.ok(user);

    }

    @GetMapping("/stats/{id}")
    public ResponseEntity<byte[]> exportPdf(@PathVariable("id") Long id,
                                            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        byte[] pdf = pdfGenerator.generatePdf(id,start,end);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "exported.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);

    }
    @GetMapping("getloc/{id}")
    public ResponseEntity<Optional<Locations>> getLoc(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(locationsRepository.findById(id));
    }

}
