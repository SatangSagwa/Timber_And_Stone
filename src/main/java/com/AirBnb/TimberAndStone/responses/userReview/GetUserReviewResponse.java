package com.AirBnb.TimberAndStone.responses.userReview;

public class GetUserReviewResponse {
    private String user;
    private String host;
    private Integer rating;
    private String review;

    public GetUserReviewResponse(String user, String host, Integer rating, String review) {
        this.user = user;
        this.host = host;
        this.rating = rating;
        this.review = review;
    }

    public GetUserReviewResponse() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
