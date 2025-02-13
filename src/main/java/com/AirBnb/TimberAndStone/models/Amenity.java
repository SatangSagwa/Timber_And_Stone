package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Amenity {

    @NotNull(message = "AmenityTitle cant be null")
    private AmenityTitle amenityTitle;

    @NotEmpty(message = "Description cant be empty")
    @Max(value = 500 )
    private String description;

//--------------------------------------------- Constructor ------------------------------------------------------------

    public Amenity() {
    }


//--------------------------------------------- Getter & Setters -------------------------------------------------------


    public @NotNull(message = "AmenityTitle cant be null") AmenityTitle getAmenityTitle() {
        return amenityTitle;
    }

    public void setAmenityTitle(@NotNull(message = "AmenityTitle cant be null") AmenityTitle amenityTitle) {
        this.amenityTitle = amenityTitle;
    }

    public @NotEmpty(message = "Description cant be empty") @Max(value = 500) String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "Description cant be empty") @Max(value = 500) String description) {
        this.description = description;
    }
}
