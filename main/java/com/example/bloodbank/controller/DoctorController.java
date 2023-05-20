package com.example.bloodbank.controller;

import com.example.bloodbank.entity.Appointment;
import com.example.bloodbank.entity.Locations;
import com.example.bloodbank.repo.AppointmentRepository;
import com.example.bloodbank.service.AppointmentService;
import com.example.bloodbank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/getapp/{data}/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentToday(@PathVariable("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
                                                                 @PathVariable("id") Long id) {

        Locations loc = userService.getLocationForUser(id);
        List<Appointment> app = appointmentService.getappDoc(data, loc.getId(), id);
        return ResponseEntity.ok(app);
    }

    @GetMapping("/allapp")
    public ResponseEntity<List<Appointment>> getAllApp(@RequestParam("id") Long id, @RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Locations loc = userService.getLocationForUser(id);
        List<Appointment> app = appointmentRepository.findByIdAndLocations_Id(loc.getId(), id, pageable);
        return ResponseEntity.ok(app);
    }

    @PostMapping("allapp/{id}")
    public ResponseEntity<Appointment> confirmApp(
            @PathVariable("id") Long id

    ) throws Exception {
        Appointment updatedAppointment = appointmentService.updateConfirmation(id, true);
        return ResponseEntity.ok(updatedAppointment);
    }

    @PostMapping("allappd/{id}")
    public ResponseEntity<Appointment> denyApp(
            @PathVariable("id") Long id

    ) throws Exception {
        Appointment updatedAppointment = appointmentService.updateConfirmation(id, false);
        return ResponseEntity.ok(updatedAppointment);
    }
}
