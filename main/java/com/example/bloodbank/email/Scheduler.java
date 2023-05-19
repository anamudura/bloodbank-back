package com.example.bloodbank.email;

import com.example.bloodbank.appuser.Appointment;
import com.example.bloodbank.appuser.Users;
import com.example.bloodbank.repo.AppointmentRepository;
import com.example.bloodbank.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class Scheduler {

    private final JavaMailSender javaMailSender;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 18 * * ?")
    public void sendReminderEmails() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Appointment> appointments = appointmentRepository.findByProg(tomorrow);
        for (Appointment appointment : appointments) {
            Users donor = userRepository.findByAppId(appointment.getId());
            String subject = "Appointment Reminder";
            String message = "Dear " + donor.getNume() + ",\n\nThis is a reminder for your appointment tomorrow at " + appointment.getProg() + ".\n\nThank you.";

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(donor.getEmail());
            email.setSubject(subject);
            email.setText(message);

            javaMailSender.send(email);
        }
    }
}

