package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Rating {

    @NotNull
    @Min(value = 0, message = "Average rating can not be below 0")
    @Max(value = 5, message = "Average rating ")
    private Double averageRating;
    private Integer numberOfRatings;
}
