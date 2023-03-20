package com.example.diplomska.service;

import com.example.diplomska.model.Reservation;
import com.example.diplomska.model.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAllByRestaurantId(Long id);

    Reservation createReservation(ReservationDTO reservation);

    Reservation updateReservation(ReservationDTO dto);

    void deleteReservation(Long id);

    List<Reservation> findByUserEmail(String email);
}
