package com.grapeup.training.kafkaproducer.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationPostDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Range(min = 1, max = 8)
    private Integer peopleCount;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
