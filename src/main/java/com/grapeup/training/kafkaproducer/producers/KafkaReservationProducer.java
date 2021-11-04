package com.grapeup.training.kafkaproducer.producers;

import com.grapeup.training.kafkaproducer.models.Reservation;
import com.grapeup.training.kafkaproducer.services.ReservationsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@AllArgsConstructor
public class KafkaReservationProducer implements ReservationProducer {
    private final Logger logger = LoggerFactory.getLogger(ReservationsService.class);
    private KafkaTemplate<String, Reservation> kafkaTemplate;

    @Override
    public void sendCreateReservationMessage(Reservation reservation) {
        logger.debug("Sending create reservation message for reservation {}", reservation);

        ListenableFuture<SendResult<String, Reservation>>
                future = kafkaTemplate.send(KafkaTopicConfig.CREATE_RESERVATION_TOPIC, reservation);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Reservation> result) {
                logger.debug("Reservation [{}] delivered with offset {}", reservation,
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.debug("Unable to deliver reservation [{}]. {}", reservation, ex.getMessage());
            }
        });
    }
}
