package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class Period {

    @NotNull(message = "startDate cant be null")
    private LocalDate startDate;

    @NotNull(message = "endDate cant be null")
    private LocalDate endDate;

    public Period() {
    }

    public @NotNull(message = "startDate cant be null") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull(message = "startDate cant be null") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull(message = "endDate cant be null") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull(message = "endDate cant be null") LocalDate endDate) {
        this.endDate = endDate;
    }
}
