package com.AirBnb.TimberAndStone.responses.booking;

import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.Period;

public class AllBookingsResponse {
    private String rental;
    private String guest;
    private Period period;
    private Double totalPrice;
    private BookingStatus status;

    public AllBookingsResponse() {
    }

    public AllBookingsResponse(String rental, String guest, Period period, Double totalPrice, BookingStatus status) {
        this.rental = rental;
        this.guest = guest;
        this.period = period;
        this.totalPrice = totalPrice;
        this.status = status;
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
}
