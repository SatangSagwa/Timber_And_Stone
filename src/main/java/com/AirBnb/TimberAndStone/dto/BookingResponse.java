package com.AirBnb.TimberAndStone.dto;

import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.Period;

public class BookingResponse {
    private String message;
    private String rentalTitle;
    private String user;
    private Period period;
    private Double totalPrice;
    private String note;
    private BookingStatus status;
    private Boolean isPaid;

    public BookingResponse() {
    }

    public BookingResponse(String message, String rentalTitle, Period period, Double totalPrice, String note, BookingStatus status) {
        this.message = message;
        this.rentalTitle = rentalTitle;
        this.period = period;
        this.totalPrice = totalPrice;
        this.note = note;
        this.status = status;
    }

    public BookingResponse(String rentalTitle, String user, Period period, Double totalPrice, Boolean isPaid, BookingStatus status) {
        this.rentalTitle = rentalTitle;
        this.user = user;
        this.period = period;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
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
