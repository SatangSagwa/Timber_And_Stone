package com.AirBnb.TimberAndStone.requests.userReview;

public class UserReviewRequest {

    private String bookingId;

    private Integer rating;

    private String review;

    public UserReviewRequest() {
    }

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
