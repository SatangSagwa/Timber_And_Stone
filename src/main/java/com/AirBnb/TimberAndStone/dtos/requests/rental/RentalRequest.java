package com.AirBnb.TimberAndStone.dtos.requests.rental;

import com.AirBnb.TimberAndStone.models.Address;
import com.AirBnb.TimberAndStone.models.Amenity;
import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.models.Period;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

public class RentalRequest {


    private String title;
    private List<String> photos;
    @Min(value = 1, message = "Price has to be a positive number")
    private Double pricePerNight;
    @Valid
    private Address address;
    private Category category;
    @Valid
    private List<Amenity> amenities;
    @Min(value = 1, message = "Capacity has to be a positive number")
    private Integer capacity;
    @Valid
    private List<Period> availablePeriods;
    private String description;

    private String policy;


//--------------------------------------------- Constructors ------------------------------------------------------------


    public RentalRequest() {
    }

    public RentalRequest(String title, List<String> photos, Double pricePerNight, Address address, Category category, List<Amenity> amenities, Integer capacity, List<Period> availablePeriods, String description, String policy) {
        this.title = title;
        this.photos = photos;
        this.pricePerNight = pricePerNight;
        this.address = address;
        this.category = category;
        this.amenities = amenities;
        this.capacity = capacity;
        this.availablePeriods = availablePeriods;
        this.description = description;
        this.policy = policy;
    }

    //--------------------------------------------- Getters ------------------------------------------------------------


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public @Valid Address getAddress() {
        return address;
    }

    public void setAddress(@Valid Address address) {
        this.address = address;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public @Valid List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(@Valid List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public @Valid List<Period> getAvailablePeriods() {
        return availablePeriods;
    }

    public void setAvailablePeriods(@Valid List<Period> availablePeriods) {
        this.availablePeriods = availablePeriods;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}
