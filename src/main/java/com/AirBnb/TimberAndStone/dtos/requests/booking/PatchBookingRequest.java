package com.AirBnb.TimberAndStone.dtos.requests.booking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class PatchBookingRequest {

    @NotNull(message = "Number of guests can not be null")
    @Min(value = 1, message = "You have to book for at least one guest!")
    private Integer numberOfGuests;

    @NotNull(message = "startDate cant be null.")
    private LocalDate startDate;

    @NotNull(message = "endDate cant be null.")
    private LocalDate endDate;

    @NotNull(message = "Note can not be null")
    @Size(max = 300, message = "Note can not exceed 300 characters.")
    //Can be empty, in case the customer has no notes fo the host.
    private String note;

    public PatchBookingRequest() {
    }

    public @NotNull(message = "Number of guests can not be null") @Min(value = 1, message = "You have to book for at least one guest!") Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public @NotNull(message = "startDate cant be null.") LocalDate getStartDate() {
        return startDate;
    }

    public @NotNull(message = "endDate cant be null.") LocalDate getEndDate() {
        return endDate;
    }

    public @NotNull(message = "Note can not be null") @Size(max = 300, message = "Note can not exceed 300 characters.") String getNote() {
        return note;
    }
}
