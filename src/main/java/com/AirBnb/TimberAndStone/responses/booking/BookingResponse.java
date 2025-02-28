package com.AirBnb.TimberAndStone.responses.booking;

import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.Period;

import java.time.LocalDateTime;

public class BookingResponse {
    private String bookingNumber;
    private String rental;
    private String guest;
    private Integer numberOfGuests;
    private Period period;
    private Double totalPrice;
    private BookingStatus status;
    private String note;
    private LocalDateTime bookingDate;

    public BookingResponse() {
    }

    public BookingResponse(String bookingNumber, String rental, String guest, Integer numberOfGuests, Period period, Double totalPrice, BookingStatus status, String note, LocalDateTime bookingDate) {
        this.bookingNumber = bookingNumber;
        this.rental = rental;
        this.guest = guest;
        this.numberOfGuests = numberOfGuests;
        this.period = period;
        this.totalPrice = totalPrice;
        this.status = status;
        this.note = note;
        this.bookingDate = bookingDate;
    }


    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
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

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
}
