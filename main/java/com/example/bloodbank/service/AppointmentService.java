package com.example.bloodbank.service;

import com.example.bloodbank.appuser.Appointment;
import com.example.bloodbank.dto.AppointmentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public interface AppointmentService {
    Appointment save(AppointmentDto appointmentDto);
    Appointment getApp(Long id);
    List<Appointment> getAppoint(LocalDate nume);
    List<Appointment> getappDoc(LocalDate date, Long id1, Long id2);
   // List<Appointment> getAllAp(Long id1, Long id2);

}
