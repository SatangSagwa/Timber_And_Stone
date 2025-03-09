package com.AirBnb.TimberAndStone.dtos.responses.booking;

import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.Period;

public class PostBookingResponse {
    private String message;
    private String rentalTitle;
    private Period period;
    private Double totalPrice;
    private String note;
    private BookingStatus status;

    public PostBookingResponse() {
    }

    public PostBookingResponse(String message, String rentalTitle, Period period, Double totalPrice, String note, BookingStatus status) {
        this.message = message;
        this.rentalTitle = rentalTitle;
        this.period = period;
        this.totalPrice = totalPrice;
        this.note = note;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRentalTitle() {
        return rentalTitle;
    }

    public void setRentalTitle(String rentalTitle) {
        this.rentalTitle = rentalTitle;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
