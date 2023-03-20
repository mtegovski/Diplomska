package com.example.diplomska.controller;

import com.example.diplomska.model.City;
import com.example.diplomska.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getAllCities() {
        return this.cityService.findAllCities();
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        City city = this.cityService.findCityById(id).orElseThrow(() -> {
            throw new NoSuchElementException("No city with that ID");
        });
        return city;
    }
}
