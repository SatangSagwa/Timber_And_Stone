package com.AirBnb.TimberAndStone.dto;

import com.AirBnb.TimberAndStone.models.Rental;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class RentalReviewResponse {
private String message;
@DBRef
private Rental toRental;
private Integer rating;


    public RentalReviewResponse(String message, Rental toRental, Integer rating) {
        this.message = message;
        this.toRental = toRental;
        this.rating = rating;
    }
    public RentalReviewResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Rental getToRental() {
        return toRental;
    }

    public void setToRental(Rental toRental) {
        this.toRental = toRental;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
