package com.AirBnb.TimberAndStone.dtos.requests.rentalReview;

public class PatchRentalReviewRequest {

    private Integer rating;
    private String review;

    public PatchRentalReviewRequest() {
    }

    public Integer getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}
