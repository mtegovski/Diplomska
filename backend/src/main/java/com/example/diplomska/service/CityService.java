package com.example.diplomska.service;

import com.example.diplomska.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<City> findAllCities();
    Optional<City> findCityById(Long id);
}
