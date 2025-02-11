package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class Period {

    @NotNull
    private LocalDate start;

    @NotNull
    private LocalDate end;
}
