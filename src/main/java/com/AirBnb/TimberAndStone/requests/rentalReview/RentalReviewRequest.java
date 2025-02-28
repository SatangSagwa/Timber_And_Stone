package com.AirBnb.TimberAndStone.requests.rentalReview;

public class RentalReviewRequest {
    private String bookingNumber;
    private Integer rating;
    private String review;

    public RentalReviewRequest() {
    }


// --------------------- Getters ------------------------------------------------


    public String getBookingNumber() {
        return bookingNumber;
    }

    public Integer getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}
