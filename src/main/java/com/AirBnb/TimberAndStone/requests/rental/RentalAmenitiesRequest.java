package com.AirBnb.TimberAndStone.requests.rental;

import com.AirBnb.TimberAndStone.models.Amenity;

import java.util.List;

public class RentalAmenitiesRequest {
    private List<Amenity> amenities;

    public RentalAmenitiesRequest() {
    }

    public RentalAmenitiesRequest(List<Amenity> amenities) {
        this.amenities = amenities;
    }


    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }


}
