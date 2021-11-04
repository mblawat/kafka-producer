package com.grapeup.training.kafkaproducer.models;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
    private String firstName;
    private String lastName;
    private Integer peopleCount;
    private LocalDate startDate;
    private LocalDate endDate;
}
