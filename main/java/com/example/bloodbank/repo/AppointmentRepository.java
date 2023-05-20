package com.example.bloodbank.repo;

import com.example.bloodbank.entity.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    Appointment getById(Long id);

    List<Appointment> findByProg(LocalDate prog);

    List<Appointment> findByProgAndIdIs(LocalDate prog, Long id);

    @Query("select a from Appointment a where a.locations.id = ?1 and a.locations.user.id = ?2 and a.prog = ?3")
    List<Appointment> findByLocations_IdAndLocations_User_IdAndProg(Long id, Long id1, LocalDate prog);

    @Query("select a from Appointment a where a.locations.id = ?1 and a.locations.user.id = ?2")
    List<Appointment> findByIdAndLocations_Id(Long id, Long id1, Pageable pageable);

    @Query("select a from Appointment a where a.locations.id = ?1")
    List<Appointment> findByLocations_Id(Long id);




}
