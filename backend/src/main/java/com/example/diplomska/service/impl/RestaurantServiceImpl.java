package com.example.diplomska.service.impl;

import com.example.diplomska.model.City;
import com.example.diplomska.model.Restaurant;
import com.example.diplomska.model.dto.RestaurantDTO;
import com.example.diplomska.repository.CityRepository;
import com.example.diplomska.repository.RestaurantRepository;
import com.example.diplomska.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CityRepository cityRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, CityRepository cityRepository) {
        this.restaurantRepository = restaurantRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<Restaurant> findAll() {
        return this.restaurantRepository.findAll();
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return this.restaurantRepository.findById(id);
    }

    @Override
    public Restaurant createRestaurant(RestaurantDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setRestaurantCategory(dto.getRestaurantCategory());
        restaurant.setNumberOfPhotos(dto.getNumberOfPhotos());
        restaurant.setRating(dto.getRating());
        City city = this.cityRepository.findById(dto.getCityId()).orElseThrow();
        restaurant.setCity(city);
        return this.restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(RestaurantDTO dto, Long id) {
        Restaurant restaurant = this.restaurantRepository.findById(id).orElseThrow();
        restaurant.setName(dto.getName());
        restaurant.setRestaurantCategory(dto.getRestaurantCategory());
        restaurant.setNumberOfPhotos(dto.getNumberOfPhotos());
        restaurant.setRating(dto.getRating());
        City city = this.cityRepository.findById(dto.getCityId()).orElseThrow();
        restaurant.setCity(city);
        return this.restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        this.restaurantRepository.deleteById(id);
    }
}
