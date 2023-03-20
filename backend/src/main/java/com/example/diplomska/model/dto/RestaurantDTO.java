package com.example.diplomska.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    private Long id;

    private String name;

    private Integer rating;

    private Integer maxCapacity;

    private String restaurantCategory;

    private Integer numberOfPhotos;

    private Long cityId;
}
