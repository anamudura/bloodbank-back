package com.example.bloodbank.service;

import com.example.bloodbank.appuser.Appointment;
import com.example.bloodbank.appuser.BankStatistics;
import com.example.bloodbank.repo.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class BankServiceImpl implements BankService{

    private final AppointmentRepository appointmentRepository;
    @Override
    public BankStatistics calculateStats(Long id, LocalDate start, LocalDate end) {
        BankStatistics bankStatistics = new BankStatistics();
        List<Appointment> totalapp = appointmentRepository.findByLocations_Id(id);
        int totalApp = 0;
        float numberLiters = 1;
        int totalA = 0;
        int totalB = 0;
        int totalO = 0;
        int totalAB = 0;
        int totalConfi = 0;
        for(Appointment app: totalapp) {
            if (app.getProg().isAfter(start) && app.getProg().isBefore(end)) {
                totalApp += 1;
                if (app.getBloodtype().contains("AB")) {
                    totalAB += 1;
                    continue;
                }
                if (app.getBloodtype().contains("A"))
                    totalA += 1;
                if (app.getBloodtype().contains("B"))
                    totalB += 1;
                if (app.getBloodtype().contains("O"))
                    totalAB += 1;
                if (app.getConfirmed() != null)
                    totalConfi += 1;
            }


        }
        bankStatistics.setNumberOfTotalAppointments(totalApp);
        bankStatistics.setA(totalA);
        bankStatistics.setB(totalB);
        bankStatistics.setO(totalO);
        bankStatistics.setAB(totalAB);
        bankStatistics.setNumberOfConfirmedAppointments(totalConfi);
        numberLiters = 0.48f * totalConfi; //trebuie modificat in confirmed app cand o sa reusesc
        //Update: am reusit
        bankStatistics.setNumberOfLiters(numberLiters);



        return bankStatistics;
    }
}
