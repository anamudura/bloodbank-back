package com.example.bloodbank.repo;

import com.example.bloodbank.appuser.BankStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BankRepository extends JpaRepository<BankStatistics, Long> {

}
