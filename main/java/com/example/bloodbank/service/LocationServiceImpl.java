package com.example.bloodbank.service;

import com.example.bloodbank.entity.Locations;
import com.example.bloodbank.repo.LocationsRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Service
@Transactional
public class LocationServiceImpl implements LocationService{
    private final LocationsRepository locationsRepository;
    @Override
    public List<Locations> getAllLocations() {
        return locationsRepository.findAll();
    }
}
