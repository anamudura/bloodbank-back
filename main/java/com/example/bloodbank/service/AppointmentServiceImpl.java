package com.example.bloodbank.service;

import com.example.bloodbank.entity.Appointment;
import com.example.bloodbank.entity.Locations;
import com.example.bloodbank.entity.Users;
import com.example.bloodbank.dto.AppointmentDto;
import com.example.bloodbank.email.EmailBuilder;
import com.example.bloodbank.email.Scheduler;
import com.example.bloodbank.repo.AppointmentRepository;
import com.example.bloodbank.repo.LocationsRepository;
import com.example.bloodbank.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final Scheduler scheduler;
    private final LocationsRepository locationsRepository;
    private final JavaMailSender javaMailSender;

    @Override
    public Appointment save(AppointmentDto appointmentDto, Long id1, Long id2) {
        Appointment app =
                new Appointment(appointmentDto.getBloodtype(),appointmentDto.getProg());
        Users user = userRepository.findById1(id1);
        Locations loc = locationsRepository.findById1(id2);
        loc.setNrmaximdon(loc.getNrmaximdon() - 1);
        app.setLocations(loc);
        app.setUser(user);
        String email = user.getEmail();
        String link = "http://localhost:8080/appointment/{id}";
        try {
            MimeMessage emailMessage = new EmailBuilder()
                    .to(email)
                    .subject("Appointment confirmation")
                    .message(buildEmail(user.getNume(), link))
                    .buildHtml(javaMailSender);

            javaMailSender.send(emailMessage);
        } catch (MessagingException e) {
            System.out.println("Ioi eroare la trimitere");
        }
        scheduler.sendReminderEmails();
        return appointmentRepository.save(app);

    }

    @Override
    public List<Appointment> getAppoint(LocalDate nume) {
        System.out.println(nume);
        return appointmentRepository.findByProg(LocalDate.now());
    }

    @Override
    public List<Appointment> getappDoc(LocalDate date, Long id1, Long id2) {
        return appointmentRepository.findByLocations_IdAndLocations_User_IdAndProg(id1,id2,LocalDate.now());
    }

    @Override
    public Appointment updateConfirmation(Long appointmentId, Boolean confirmed) throws Exception {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new Exception("Not found"));
        if(!confirmed)
            appointment.getLocations().setNrmaximdon(appointment.getLocations().getNrmaximdon() + 1);
        appointment.setConfirmed(confirmed);
        return appointmentRepository.save(appointment);
    }
    
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Appointment confirmation</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for making an appointment with us. Our registration system will send u an email 1 day before your appointment: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


}
