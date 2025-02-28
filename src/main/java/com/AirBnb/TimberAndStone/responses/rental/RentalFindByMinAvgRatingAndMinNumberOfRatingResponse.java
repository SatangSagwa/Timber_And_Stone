package com.AirBnb.TimberAndStone.responses.rental;

public class RentalFindByMinAvgRatingAndMinNumberOfRatingResponse {

    private String title;
    private Double averageRating;
    private Integer numberOfRatings;



//--------------------------------------------- Constructors ------------------------------------------------------------

    public RentalFindByMinAvgRatingAndMinNumberOfRatingResponse() {
    }

    public RentalFindByMinAvgRatingAndMinNumberOfRatingResponse(String title, Double averageRating, Integer numberOfRatings) {
        this.title = title;
        this.averageRating = averageRating;
        this.numberOfRatings = numberOfRatings;
    }

    //--------------------------------------------- Getter & Setters -------------------------------------------------------


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

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }
}

