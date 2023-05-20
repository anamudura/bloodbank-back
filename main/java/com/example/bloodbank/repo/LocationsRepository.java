package com.example.bloodbank.repo;

import com.example.bloodbank.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LocationsRepository extends JpaRepository<Locations, Long> {
    @Query("SELECT l from Locations l where l.id = ?1")
    Locations findById1(Long aLong);
}
