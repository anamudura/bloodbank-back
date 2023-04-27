package com.example.bloodbank.repo;

import com.example.bloodbank.appuser.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LocationsRepository extends JpaRepository<Locations, Long> {
    //Optional<Locations> findById(Long id);
    //List<Locations> getAllById();
}
