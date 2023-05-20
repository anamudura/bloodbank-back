package com.example.bloodbank.service;

import com.example.bloodbank.entity.BankStatistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public interface BankService {

    BankStatistics calculateStats(Long id, LocalDate start, LocalDate end);
}
