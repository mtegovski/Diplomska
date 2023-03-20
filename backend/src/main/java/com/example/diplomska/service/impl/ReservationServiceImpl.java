package com.example.diplomska.service.impl;

import com.example.diplomska.model.Reservation;
import com.example.diplomska.model.Restaurant;
import com.example.diplomska.model.User;
import com.example.diplomska.model.dto.ReservationDTO;
import com.example.diplomska.repository.ReservationRepository;
import com.example.diplomska.repository.RestaurantRepository;
import com.example.diplomska.repository.UserRepository;
import com.example.diplomska.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }


    @Override
    public List<Reservation> findAllByRestaurantId(Long id) {
        return this.reservationRepository.findReservationsByRestaurantId(id);
    }

    @Override
    public Reservation createReservation(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        Restaurant restaurant = this.restaurantRepository.findById(dto.getRestaurantId()).orElseThrow(RuntimeException::new);
        User user = this.userRepository.findUserByEmail(dto.getEmail()).orElseThrow(RuntimeException::new);
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);
        reservation.setRestaurantName(restaurant.getName());
        dto.setDate(dto.getDate().truncatedTo(ChronoUnit.MINUTES));
        reservation.setDate(dto.getDate());
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        this.sendConfirmReservationMail(reservation);
        return this.reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(ReservationDTO dto) {
        Reservation reservation = this.reservationRepository.findById(dto.getId()).orElseThrow(RuntimeException::new);
        dto.setDate(dto.getDate().truncatedTo(ChronoUnit.MINUTES));
        reservation.setDate(dto.getDate());
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        this.sendEditReservationMail(reservation);
        return this.reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = this.reservationRepository.findById(id).orElseThrow(RuntimeException::new);
        this.reservationRepository.deleteById(id);
        this.sendDeleteReservationMail(reservation);
    }

    @Override
    public List<Reservation> findByUserEmail(String email) {
        List<Reservation> reservations = this.reservationRepository.findReservationsByUserEmail(email);
        reservations = reservations
                .stream()
                .filter(r -> r.getDate().isAfter(LocalDateTime.now()))
                .sorted()
                .collect(Collectors.toList());
        return reservations;
    }

    private void sendConfirmReservationMail(Reservation reservation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setReplyTo("noreply@fatimasa.com");
        message.setTo(reservation.getUser().getEmail());
        message.setSubject("Потврдена резервација во " + reservation.getRestaurant().getName());
        String text = "Почитувани,\n" +
                "Успешно е направена резервација во " + reservation.getRestaurant().getName() +
                " за " + reservation.getDate().toLocalDate() +
                " во " + reservation.getDate().toLocalTime() +
                " за " + reservation.getNumberOfGuests() + " личности.\n"
                + "Поздрав,\nФати Маса";
        message.setText(text);
        javaMailSender.send(message);
    }

    private void sendEditReservationMail(Reservation reservation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@fatimasa.com");
        message.setTo(reservation.getUser().getEmail());
        String text = "Почитувани,\n" +
                "Успешно е ажурирана резервација во " + reservation.getRestaurant().getName() +
                " за " + reservation.getDate().toLocalDate() +
                " во " + reservation.getDate().toLocalTime() +
                " за " + reservation.getNumberOfGuests() + " личности.\n"
                + "Поздрав,\nФати Маса";
        message.setText(text);
        message.setSubject("Изменета резервација во " + reservation.getRestaurant().getName());
        javaMailSender.send(message);
    }

    private void sendDeleteReservationMail(Reservation reservation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@fatimasa.com");
        message.setTo(reservation.getUser().getEmail());
        message.setSubject("Откажана резервација во " + reservation.getRestaurant().getName());
        String text = "Почитувани,\n" +
                "Успешно е откажана резервација во " + reservation.getRestaurant().getName() +
                " за " + reservation.getDate().toLocalDate() +
                " во " + reservation.getDate().toLocalTime() +
                " за " + reservation.getNumberOfGuests() + " личности.\n"
                + "Поздрав,\nФати Маса";
        message.setText(text);
        javaMailSender.send(message);
    }
}
