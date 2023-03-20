package com.example.diplomska.repository;

import com.example.diplomska.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAllByNameContaining(String name);
    List<Restaurant> findAllByCityId(Long cityId);
    List<Restaurant> findAllByRating(Integer rating);
}
