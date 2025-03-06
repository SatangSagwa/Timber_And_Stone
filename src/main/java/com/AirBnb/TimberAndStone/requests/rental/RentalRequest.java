package com.AirBnb.TimberAndStone.requests.rental;

import com.AirBnb.TimberAndStone.models.Address;
import com.AirBnb.TimberAndStone.models.Amenity;
import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.models.Period;
import jakarta.validation.Valid;

import java.util.List;

public class RentalRequest {

    //@NotNull(message = "Title can not be null")
    //@NotEmpty(message = "Title can not be empty")
    //@Size(min = 1, max = 50, message = "Title has to be between 1-50 characters")
    private String title;

    //@NotNull(message = "Photos can not be null")
    //@Size(max = 10, message = "You can add max 10 photos")
    private List<String> photos;

    //@NotNull(message = "Price per night can not be null")
    //@Min(value = 1, message = "PricePerNight have to be minimum 1")
    //@Max(value = 1000000, message = "PricePerNight have to be max 1 000 000")
    private Double pricePerNight;

    //@NotNull(message = "Address can not be null")
    @Valid
    private Address address;

    //@NotNull(message = "Category can not be null")
    private Category category;

    //@NotNull(message = "Amenities can not be null")
    @Valid
    private List<Amenity> amenities;

    //@NotNull(message = "Capacity can not be null")
    private Integer capacity;

    //@NotNull(message = "Periods can not be null")
    @Valid
    private List<Period> availablePeriods;

    //@NotNull(message = "Description can not be null")
    //@NotEmpty(message = "Description can not be empty")
    //@Size(min = 1, max = 500, message = "Description has to be between 1-500 characters")
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
