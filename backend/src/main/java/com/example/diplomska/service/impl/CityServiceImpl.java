package com.example.diplomska.service.impl;

import com.example.diplomska.model.City;
import com.example.diplomska.model.Restaurant;
import com.example.diplomska.repository.CityRepository;
import com.example.diplomska.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> findAllCities() {
        return this.cityRepository.findAll();
    }

    @Override
    public Optional<City> findCityById(Long id) {
        return this.cityRepository.findById(id);
    }
}
