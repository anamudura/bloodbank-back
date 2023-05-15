package com.example.bloodbank.service;

import com.example.bloodbank.appuser.Appointment;
import com.example.bloodbank.appuser.BankStatistics;
import com.example.bloodbank.repo.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class BankServiceImpl implements BankService{

    private final AppointmentRepository appointmentRepository;
    @Override
    public BankStatistics calculateStats(Long id) {
        BankStatistics bankStatistics = new BankStatistics();
        List<Appointment> totalapp = appointmentRepository.findByLocations_Id(id);
        int totalApp = 0;
        float numberLiters = 1;
        int totalA = 0;
        int totalB = 0;
        int totalO = 0;
        int totalAB = 0;
        for(Appointment app: totalapp) {
            totalApp += 1;
            if(app.getBloodtype().contains("AB")) {
                totalAB += 1;
                continue;
            }
            if(app.getBloodtype().contains("A"))
                totalA += 1;
            if(app.getBloodtype().contains("B"))
                totalB += 1;
            if(app.getBloodtype().contains("O"))
                totalAB += 1;

        }
        bankStatistics.setNumberOfTotalAppointments(totalApp);
        bankStatistics.setA(totalA);
        bankStatistics.setB(totalB);
        bankStatistics.setO(totalO);
        bankStatistics.setAB(totalAB);
        numberLiters = 0.48f * totalApp; //trebuie modificat in confirmed app cand o sa reusesc
        bankStatistics.setNumberOfLiters(numberLiters);



        return bankStatistics;
    }
}
