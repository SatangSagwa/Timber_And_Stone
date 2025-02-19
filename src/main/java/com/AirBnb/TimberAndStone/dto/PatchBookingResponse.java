package com.AirBnb.TimberAndStone.dto;

import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.Period;

public class PatchBookingResponse {
    private String message;
    private String rental;
    private String bookingNumber;
    private String guest;
    private Integer numberOfGuests;
    private Period period;
    private Double totalPrice;
    private Boolean isPaid;
    private BookingStatus status;
    private String note;

    public PatchBookingResponse() {
    }

    public PatchBookingResponse(String message, String rental, String bookingNumber, String guest, Integer numberOfGuests, Period period, Double totalPrice, Boolean isPaid, BookingStatus status, String note) {
        this.message = message;
        this.rental = rental;
        this.bookingNumber = bookingNumber;
        this.guest = guest;
        this.numberOfGuests = numberOfGuests;
        this.period = period;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.status = status;
        this.note = note;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
