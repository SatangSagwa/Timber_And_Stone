package com.AirBnb.TimberAndStone.requests.userReview;

public class PatchUserReviewRequest {

    private Integer rating;
    private String review;

    public PatchUserReviewRequest() {
    }

    public Integer getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}

