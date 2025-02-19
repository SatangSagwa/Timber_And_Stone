package com.AirBnb.TimberAndStone.dto;

import com.AirBnb.TimberAndStone.models.Amenity;

import java.util.List;

public class RentalAmenitiesDTO {
    private List<Amenity> amenities;

    public RentalAmenitiesDTO() {
    }

    public RentalAmenitiesDTO(List<Amenity> amenities) {
        this.amenities = amenities;
    }


    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }


}
