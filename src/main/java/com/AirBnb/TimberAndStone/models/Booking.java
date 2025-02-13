package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    @DBRef
    @NotNull(message = "User can not be null")
    private User user;

    @DBRef
    @NotNull(message = "Rental can not be null")
    private Rental rental;

    @NotNull(message = "Period can not be null")
    private Period period;

    @NotNull(message = "totalPrice can not be null")
    @Min(value = 1, message = "Price has to be at least $1")
    //No max since it is dependent on the length of the period and the Rental.pricePerNight.
    private Double totalPrice;

    @NotNull(message = "isPaid can not be null")
    private Boolean isPaid;

    @NotNull(message = "Booking status can not be null")
    private BookingStatus bookingStatus;

    @NotNull(message = "Note can not be null")
    @Size(max = 300, message = "Note can not exceed 300 characters.")
    //Can be empty, in case the customer has no notes fo the host.
    private String note;

    @NotNull
    private LocalDate createdAt;

    @NotNull
    private LocalDate updatedAt;


//--------------------------------------------- Constructor ------------------------------------------------------------

    public Booking() {
    }

//--------------------------------------------- Getter & Setters -------------------------------------------------------

    public String getId() {
        return id;
    }

    public @NotNull(message = "User can not be null") User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "User can not be null") User user) {
        this.user = user;
    }

    public @NotNull(message = "Rental can not be null") Rental getRental() {
        return rental;
    }

    public void setRental(@NotNull(message = "Rental can not be null") Rental rental) {
        this.rental = rental;
    }

    public @NotNull(message = "Period can not be null") Period getPeriod() {
        return period;
    }

    public void setPeriod(@NotNull(message = "Period can not be null") Period period) {
        this.period = period;
    }

    public @NotNull(message = "totalPrice can not be null") @Min(value = 1, message = "Price had to be at least $1") Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(@NotNull(message = "totalPrice can not be null") @Min(value = 1, message = "Price had to be at least $1") Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public @NotNull(message = "isPaid can not be null") Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(@NotNull(message = "isPaid can not be null") Boolean paid) {
        isPaid = paid;
    }


    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public @NotNull(message = "Note can not be null") String getNote() {
        return note;
    }

    public void setNote(@NotNull(message = "Note can not be null") String note) {
        this.note = note;
    }

    public @NotNull LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
