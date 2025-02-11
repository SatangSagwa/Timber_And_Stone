package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class Period {

    @NotNull(message = "startDate cant be null")
    private LocalDate startDate;

    @NotNull(message = "endDate cant be null")
    private LocalDate endDate;
}
