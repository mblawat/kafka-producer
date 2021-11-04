package com.grapeup.training.kafkaproducer.producers;

import com.grapeup.training.kafkaproducer.models.Reservation;

public interface ReservationProducer {
    void sendCreateReservationMessage(Reservation reservation);
}
