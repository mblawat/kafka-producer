package com.grapeup.training.kafkaproducer.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationResponseDTO {
    private String firstName;
    private String lastName;
    private Integer peopleCount;
    private LocalDate startDate;
    private LocalDate endDate;
}
