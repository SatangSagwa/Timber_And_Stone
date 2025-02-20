package com.AirBnb.TimberAndStone.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    @NotNull(message = "Booking number can not be null")
    private String bookingNumber;

    @DBRef
    @NotNull(message = "User can not be null")
    @Valid
    private User user;

    @NotNull(message = "Number of guests can not be null")
    @Min(value = 1, message = "You have to book for at least one guest!")
    private Integer numberOfGuests;

    @DBRef
    @NotNull(message = "Rental can not be null")
    @Valid
    private Rental rental;

    @NotNull(message = "Period can not be null")
    @Valid
    private Period period;

    @NotNull(message = "totalPrice can not be null")
    @Min(value = 1, message = "Price has to be at least 1")
    //No max since it is dependent on the length of the period and the Rental.pricePerNight.
    private Double totalPrice;

    @NotNull(message = "isPaid can not be null")
    private Boolean isPaid;

    @NotNull(message = "Booking status can not be null")
    @Valid
    private BookingStatus bookingStatus;

    @NotNull(message = "Note can not be null")
    @Size(max = 300, message = "Note can not exceed 300 characters.")
    //Can be empty, in case the customer has no notes fo the host.
    private String note;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


//--------------------------------------------- Constructor ------------------------------------------------------------

    public Booking() {
    }

//--------------------------------------------- Getter & Setters -------------------------------------------------------


    public String getId() {
        return id;
    }

    public @NotNull(message = "Booking number can not be null") String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(@NotNull(message = "Booking number can not be null") String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public @NotNull(message = "User can not be null") @Valid User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "User can not be null") @Valid User user) {
        this.user = user;
    }

    public @NotNull(message = "Number of guests can not be null") @Min(value = 1, message = "You have to book for at least one guest!") Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(@NotNull(message = "Number of guests can not be null") @Min(value = 1, message = "You have to book for at least one guest!") Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public @NotNull(message = "Rental can not be null") @Valid Rental getRental() {
        return rental;
    }

    public void setRental(@NotNull(message = "Rental can not be null") @Valid Rental rental) {
        this.rental = rental;
    }

    public @NotNull(message = "Period can not be null") @Valid Period getPeriod() {
        return period;
    }

    public void setPeriod(@NotNull(message = "Period can not be null") @Valid Period period) {
        this.period = period;
    }

    public @NotNull(message = "totalPrice can not be null") @Min(value = 1, message = "Price has to be at least 1") Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(@NotNull(message = "totalPrice can not be null") @Min(value = 1, message = "Price has to be at least 1") Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public @NotNull(message = "isPaid can not be null") Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(@NotNull(message = "isPaid can not be null") Boolean paid) {
        isPaid = paid;
    }

    public @NotNull(message = "Booking status can not be null") @Valid BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(@NotNull(message = "Booking status can not be null") @Valid BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public @NotNull(message = "Note can not be null") @Size(max = 300, message = "Note can not exceed 300 characters.") String getNote() {
        return note;
    }

    public void setNote(@NotNull(message = "Note can not be null") @Size(max = 300, message = "Note can not exceed 300 characters.") String note) {
        this.note = note;
    }

    public @NotNull LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
