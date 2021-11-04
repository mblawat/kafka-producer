package com.grapeup.training.kafkaproducer.services;

import com.grapeup.training.kafkaproducer.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReservationsServiceITests {
    private Reservation mockReservation;

    @Autowired
    private ReservationsService reservationsService;

    @BeforeEach
    public void setUp() {
        mockReservation =
                new Reservation("John", "Smith", 8, LocalDate.of(2021, 8, 30), LocalDate.of(2021, 9, 5));
    }

    @Test
    public void create() {
        // Act
        Reservation createdReservation = reservationsService.create(mockReservation);

        // Assert
        assertEquals(mockReservation, createdReservation);
    }

}
