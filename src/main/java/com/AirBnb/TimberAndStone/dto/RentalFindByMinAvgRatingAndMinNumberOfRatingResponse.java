package com.AirBnb.TimberAndStone.dto;

public class RentalFindByMinAvgRatingAndMinNumberOfRatingResponse {

    private String title;
    private Double averageRating;
    private Integer numberOfRating;



//--------------------------------------------- Constructors ------------------------------------------------------------

    public RentalFindByMinAvgRatingAndMinNumberOfRatingResponse() {
    }

    public RentalFindByMinAvgRatingAndMinNumberOfRatingResponse(String title, Double averageRating, Integer numberOfRating) {
        this.title = title;
        this.averageRating = averageRating;
        this.numberOfRating = numberOfRating;
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

    public Integer getNumberOfRating() {
        return numberOfRating;
    }

    public void setNumberOfRating(Integer numberOfRating) {
        this.numberOfRating = numberOfRating;
    }
}

