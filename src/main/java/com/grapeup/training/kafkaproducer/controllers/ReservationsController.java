package com.grapeup.training.kafkaproducer.controllers;

import com.grapeup.training.kafkaproducer.dtos.DTOModelMapper;
import com.grapeup.training.kafkaproducer.dtos.ReservationPostDTO;
import com.grapeup.training.kafkaproducer.dtos.ReservationResponseDTO;
import com.grapeup.training.kafkaproducer.models.Reservation;
import com.grapeup.training.kafkaproducer.services.ReservationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationsController {
    private final ReservationsService reservationsService;
    private final DTOModelMapper modelMapper;

    public ReservationsController(ReservationsService reservationsService, DTOModelMapper modelMapper) {
        this.reservationsService = reservationsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody final ReservationPostDTO reservationDTO) {
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        return ResponseEntity.accepted().body(
                modelMapper.map(reservationsService.create(reservation), ReservationResponseDTO.class));
    }
}
