package com.grapeup.training.kafkaproducer.services;

import com.grapeup.training.kafkaproducer.models.Reservation;
import com.grapeup.training.kafkaproducer.producers.ReservationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ReservationsService {
    private final Logger logger = LoggerFactory.getLogger(ReservationsService.class);
    private final ReservationProducer reservationProducer;

    public ReservationsService(ReservationProducer reservationProducer) {
        this.reservationProducer = reservationProducer;
    }

    public Reservation create(final Reservation reservation) {
        logger.debug("Adding new reservation: {}", reservation);
        reservationProducer.sendCreateReservationMessage(reservation);
        return reservation;
    }
}
