package com.example.bloodbank.service;

import com.example.bloodbank.appuser.Locations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService{
    List<Locations> getAllLocations();
}
