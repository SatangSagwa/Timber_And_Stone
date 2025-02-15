package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Amenity {

    @NotNull(message = "AmenityTitle cant be null")
    private AmenityTitle amenityTitle;

    @NotNull(message = "Description can not be null")
    @NotEmpty(message = "Description can not be empty")
    @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters")
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

    public @NotNull(message = "Description can not be null") @NotEmpty(message = "Description can not be empty") @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description can not be null") @NotEmpty(message = "Description can not be empty") @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters") String description) {
        this.description = description;
    }
}
