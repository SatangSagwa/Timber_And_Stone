package com.AirBnb.TimberAndStone.responses;

import com.AirBnb.TimberAndStone.models.User;

public class GetUserReviewResponse {
    private User fromHost;
    private User toUser;
    private int rating;
    private String review;

    public GetUserReviewResponse() {
    }

    public GetUserReviewResponse(User fromHost, User toUser, int rating, String review) {
        this.fromHost = fromHost;
        this.toUser = toUser;
        this.rating = rating;
        this.review = review;
    }
    public User getFromHost() {
        return fromHost;
    }

    public void setFromHost(User fromHost) {
        this.fromHost = fromHost;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
