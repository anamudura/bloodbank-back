package com.example.bloodbank.repo;

import com.example.bloodbank.appuser.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//ADAUGAT ULTERIOR - FOLOSESTE SECURITATE
@Repository
@Transactional
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByEmail(String email);
}
