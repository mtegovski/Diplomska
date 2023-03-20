package com.example.diplomska.controller;

import com.example.diplomska.model.Restaurant;
import com.example.diplomska.model.dto.RestaurantDTO;
import com.example.diplomska.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return this.restaurantService.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant findById(@PathVariable Long id) {
            return this.restaurantService.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("No restaurant with that ID");
        });
    }

    @PostMapping
    public Restaurant createRestaurant(@RequestBody RestaurantDTO dto) {
        return this.restaurantService.createRestaurant(dto);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDTO dto) {
        return this.restaurantService.updateRestaurant(dto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        this.restaurantService.deleteRestaurant(id);
    }

}
