package com.grapeup.training.kafkaproducer.services;

import com.grapeup.training.kafkaproducer.models.Reservation;
import com.grapeup.training.kafkaproducer.producers.ReservationProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReservationsServiceTests {
    private Reservation mockReservation;

    @MockBean
    private ReservationProducer mockReservationProducer;

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
        Reservation reservation = reservationsService.create(mockReservation);

        // Assert
        assertEquals(mockReservation, reservation);
        Mockito.verify(mockReservationProducer).sendCreateReservationMessage(mockReservation);
    }
}
