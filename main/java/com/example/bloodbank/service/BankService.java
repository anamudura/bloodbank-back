package com.example.bloodbank.service;

import com.example.bloodbank.appuser.BankStatistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface BankService {

    BankStatistics calculateStats(Long id);
}
