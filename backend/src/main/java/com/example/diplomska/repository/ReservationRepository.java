package com.example.diplomska.repository;

import com.example.diplomska.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationsByRestaurantId(Long id);
    List<Reservation> findReservationsByUserEmail(String email);
}
