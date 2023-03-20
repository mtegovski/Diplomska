package com.example.diplomska.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Long id;

    private Integer numberOfGuests;

    private LocalDateTime date;

    private Long restaurantId;

    private String email;
}
