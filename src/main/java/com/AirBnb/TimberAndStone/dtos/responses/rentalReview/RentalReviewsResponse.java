package com.AirBnb.TimberAndStone.dtos.responses.rentalReview;

public class RentalReviewsResponse {
    private String rental;
    private String reviewer;
    private Integer rating;
    private String review;

    public RentalReviewsResponse() {
    }

    public RentalReviewsResponse(String rental, String reviewer, Integer rating, String review) {
        this.rental = rental;
        this.reviewer = reviewer;
        this.rating = rating;
        this.review = review;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
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
