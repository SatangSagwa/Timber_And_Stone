package com.AirBnb.TimberAndStone.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserReviewRequest {

    private String bookingNumber;

    @NotNull
    @Min(value=1, message="Rating can not be below 1")
    @Max(value=5, message="Rating can not be above 5")
    private Integer rating;

    @Size(min=1, max=500, message="Review has to be between 1-500 characters")
    private String review;

    public UserReviewRequest() {
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    @NotNull
    @Min(value = 1, message = "Rating can not be below 1")
    @Max(value = 5, message = "Rating can not be above 5")
    public int getRating() {
        return rating;
    }

    public @Size(min = 1, max = 500, message = "Review has to be between 1-500 characters") String getReview() {
        return review;
    }
}
