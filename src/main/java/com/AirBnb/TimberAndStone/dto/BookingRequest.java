package com.AirBnb.TimberAndStone.dto;

import com.AirBnb.TimberAndStone.models.Rental;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;

public class BookingRequest {
    @DBRef
    @NotNull(message = "Rental can not be null")
    private Rental rental;

    @NotNull(message = "startDate cant be null.")
    private LocalDate startDate;

    @NotNull(message = "endDate cant be null.")
    private LocalDate endDate;

    @NotNull(message = "Note can not be null")
    @Size(max = 300, message = "Note can not exceed 300 characters.")
    //Can be empty, in case the customer has no notes fo the host.
    private String note;

    public BookingRequest() {
    }

    public @NotNull(message = "Rental can not be null") Rental getRental() {
        return rental;
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
