package com.example.diplomska.service;

import com.example.diplomska.model.Restaurant;
import com.example.diplomska.model.dto.RestaurantDTO;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);
    Restaurant createRestaurant(RestaurantDTO dto);
    Restaurant updateRestaurant(RestaurantDTO dto, Long id);
    void deleteRestaurant(Long id);

}
