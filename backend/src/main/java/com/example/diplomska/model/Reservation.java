package com.example.diplomska.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Reservation implements Comparable<Reservation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numberOfGuests;
    private LocalDateTime date;
    private String restaurantName;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_email", nullable = false)
    private User user;

    @Override
    public int compareTo(Reservation o) {
        return getDate().compareTo(o.getDate());
    }
}
