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
    Appointment save(AppointmentDto appointmentDto, Long id1, Long id2);
    List<Appointment> getAppoint(LocalDate nume);
    List<Appointment> getappDoc(LocalDate date, Long id1, Long id2);
    Appointment updateConfirmation(Long appointmentId, Boolean confirmed) throws Exception;

}
