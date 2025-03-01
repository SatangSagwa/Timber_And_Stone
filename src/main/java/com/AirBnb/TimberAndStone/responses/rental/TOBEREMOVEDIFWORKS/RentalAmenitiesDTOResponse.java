package com.AirBnb.TimberAndStone.responses.rental.TOBEREMOVEDIFWORKS;

import com.AirBnb.TimberAndStone.models.Amenity;

import java.util.List;

public class RentalAmenitiesDTOResponse {

private String title;
private List<Amenity> amenities;


    public RentalAmenitiesDTOResponse() {
    }


    public RentalAmenitiesDTOResponse(String title, List<Amenity> amenities) {
        this.title = title;
        this.amenities = amenities;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }
}



