package com.grapeup.training.kafkaproducer.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grapeup.training.kafkaproducer.models.Reservation;
import com.grapeup.training.kafkaproducer.services.ReservationsService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaReservationProducerITests {
    private BlockingQueue<ConsumerRecord<String, Reservation>> records;

    private KafkaMessageListenerContainer<String, Reservation> container;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaReservationProducer kafkaReservationProducer;

    @BeforeAll
    public void setUp() {
        DefaultKafkaConsumerFactory<String, Reservation> consumerFactory = new DefaultKafkaConsumerFactory<>(getConsumerProperties());
        ContainerProperties containerProperties = new ContainerProperties(KafkaTopicConfig.CREATE_RESERVATION_TOPIC);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, Reservation>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @AfterAll
    void tearDown() {
        container.stop();
    }

    private Map<String, Object> getConsumerProperties() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString(),
                ConsumerConfig.GROUP_ID_CONFIG, "consumer",
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true",
                ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10",
                ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                JsonDeserializer.TRUSTED_PACKAGES, "*");
    }

    @Test
    public void sendCreateReservationMessage() throws InterruptedException {
        // Arrange
        Reservation reservation =
                new Reservation("John", "Smith", 8, LocalDate.of(2021, 8, 30), LocalDate.of(2021, 9, 5));

        // Act
        kafkaReservationProducer.sendCreateReservationMessage((reservation));

        // Assert
        ConsumerRecord<String, Reservation> message = records.poll(500, TimeUnit.MILLISECONDS);
        assertNotNull(message);
        Reservation sentReservation = message.value();
        assertNotNull(sentReservation);
        assertEquals(reservation.getFirstName(), sentReservation.getFirstName());
        assertEquals(reservation.getLastName(), sentReservation.getLastName());
    }
}
