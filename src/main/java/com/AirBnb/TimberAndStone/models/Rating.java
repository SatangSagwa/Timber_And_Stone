package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Rating {

    @NotNull
    @Min(value = 0, message = "Average rating can not be below 0")
    @Max(value = 5, message = "Average rating can be max 5")
    private Double averageRating;

    @NotNull
    @Min(value = 0, message = "Number of ratings can not be below 0")
    private Integer numberOfRatings;

    public Rating() {
    }

    public @NotNull @Min(value = 0, message = "Average rating can not be below 0") @Max(value = 5, message = "Average rating can be max 5") Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(@NotNull @Min(value = 0, message = "Average rating can not be below 0") @Max(value = 5, message = "Average rating can be max 5") Double averageRating) {
        this.averageRating = averageRating;
    }

    public @NotNull @Min(value = 0, message = "Number of ratings can not be below 0") Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(@NotNull @Min(value = 0, message = "Number of ratings can not be below 0") Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }
}
