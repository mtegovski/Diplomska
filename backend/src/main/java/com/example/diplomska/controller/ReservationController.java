package com.example.diplomska.controller;

import com.example.diplomska.model.Reservation;
import com.example.diplomska.model.dto.ReservationDTO;
import com.example.diplomska.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public List<Reservation> findReservationsByRestaurantId(@PathVariable Long id) {
        return this.reservationService.findAllByRestaurantId(id);
    }

    @PostMapping
    public Reservation createReservation(@RequestBody ReservationDTO dto) {
        return this.reservationService.createReservation(dto);
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody ReservationDTO dto) {
        return this.reservationService.updateReservation(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        this.reservationService.deleteReservation(id);
    }

    @GetMapping("user")
    public List<Reservation> findReservationsForUser(@RequestParam("email") String email) {
        return this.reservationService.findByUserEmail(email);
    }

}
