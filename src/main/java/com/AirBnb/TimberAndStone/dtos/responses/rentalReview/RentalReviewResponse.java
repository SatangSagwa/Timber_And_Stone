package com.AirBnb.TimberAndStone.dtos.responses.rentalReview;

public class RentalReviewResponse {
    private String message;
    private String user;
    private String rental;
    private Integer rating;
    private String review;


    public RentalReviewResponse() {
    }

    public RentalReviewResponse(String message, String user, String rental, Integer rating, String review) {
        this.message = message;
        this.user = user;
        this.rental = rental;
        this.rating = rating;
        this.review = review;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
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
