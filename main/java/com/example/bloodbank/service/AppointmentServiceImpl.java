package com.example.bloodbank.service;

import com.example.bloodbank.appuser.Appointment;
import com.example.bloodbank.dto.AppointmentDto;
import com.example.bloodbank.repo.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentRepository appointmentRepository;
    @Override
    public Appointment save(AppointmentDto appointmentDto) {
        Appointment app =
                new Appointment(appointmentDto.getBloodtype(),appointmentDto.getProg());

        return appointmentRepository.save(app);

    }
    //SELECT COUNT PENTRU PROGRAMARI
    //SA APARA OPTIUNILE CAND SUNT AVAIBLE SLOTS PE ZILE
    //programari daily - sau programari pe luni

    @Override
    public Appointment getApp(Long id) {
        return appointmentRepository.getById(id);
    }

    @Override
    public List<Appointment> getAppoint(LocalDate nume) {
        return appointmentRepository.findByProg(LocalDate.now());
    }

    @Override
    public List<Appointment> getappDoc(LocalDate date, Long id1, Long id2) {
        return appointmentRepository.findByLocations_IdAndLocations_User_IdAndProg(id1,id2,LocalDate.now());
    }


}
