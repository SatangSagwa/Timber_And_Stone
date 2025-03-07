package com.AirBnb.TimberAndStone.dtos.requests.rentalReview;

public class RentalReviewRequest {
    private String bookingId;
    private Integer rating;
    private String review;

    public RentalReviewRequest() {
    }

    public RentalReviewRequest(String bookingId, Integer rating, String review) {
        this.bookingId = bookingId;
        this.rating = rating;
        this.review = review;
    }

    // --------------------- Getters ------------------------------------------------


    public String getBookingId() {
        return bookingId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}
