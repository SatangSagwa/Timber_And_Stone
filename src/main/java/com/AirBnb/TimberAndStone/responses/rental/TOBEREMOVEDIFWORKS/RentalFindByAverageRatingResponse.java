package com.AirBnb.TimberAndStone.responses.rental.TOBEREMOVEDIFWORKS;

public class RentalFindByAverageRatingResponse {
    private String title;
    private Double averageRating;

    public RentalFindByAverageRatingResponse() {

    }
    public RentalFindByAverageRatingResponse(String title, Double averageRating) {
        this.title = title;
        this.averageRating = averageRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
