package com.AirBnb.TimberAndStone.requests;

public class UserReviewRequest {

    private String bookingNumber;

    private Integer rating;

    private String review;

    public UserReviewRequest() {
    }

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
