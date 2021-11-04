package com.grapeup.training.kafkaproducer.controllers;

import com.grapeup.training.kafkaproducer.dtos.ReservationPostDTO;
import com.grapeup.training.kafkaproducer.dtos.ReservationResponseDTO;
import com.grapeup.training.kafkaproducer.models.Reservation;
import com.grapeup.training.kafkaproducer.services.ReservationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationsControllerITests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void create() throws Exception {
        // Arrange
        String url = "/api/v1/reservations";

        ReservationPostDTO reservation =
                new ReservationPostDTO("John", "Smith", 8, LocalDate.of(2021, 8, 30), LocalDate.of(2021, 9, 5));
        ReservationResponseDTO expectedResponse =
                new ReservationResponseDTO("John", "Smith", 8, LocalDate.of(2021, 8, 30), LocalDate.of(2021, 9, 5));

        // Act
        ResponseEntity<ReservationResponseDTO> response = restTemplate.postForEntity(url, reservation, ReservationResponseDTO.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody().equals(expectedResponse));
    }
}
