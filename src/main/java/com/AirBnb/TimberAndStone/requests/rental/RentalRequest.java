package com.AirBnb.TimberAndStone.requests.rental;

import com.AirBnb.TimberAndStone.models.Address;
import com.AirBnb.TimberAndStone.models.Amenity;
import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.models.Period;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class RentalRequest {

    @NotNull(message = "Title can not be null")
    @NotEmpty(message = "Title can not be empty")
    @Size(min = 1, max = 50, message = "Title has to be between 1-50 characters")
    private String title;

    @NotNull(message = "Photos can not be null")
    @Size(max = 10, message = "You can add max 10 photos")
    private List<String> photos;

    @NotNull(message = "Price per night can not be null")
    @Min(value = 1, message = "PricePerNight have to be minimum 1")
    @Max(value = 1000000, message = "PricePerNight have to be max 1 000 000")
    private Double pricePerNight;

    @NotNull(message = "Address can not be null")
    @Valid
    private Address address;

    @NotNull(message = "Category can not be null")
    private Category category;

    @NotNull(message = "Amenities can not be null")
    @Valid
    private List<Amenity> amenities;

    @NotNull(message = "Capacity can not be null")
    private Integer capacity;

    @NotNull(message = "Periods can not be null")
    @Valid
    private List<Period> availablePeriods;

    @NotNull(message = "Description can not be null")
    @NotEmpty(message = "Description can not be empty")
    @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters")
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


    public @NotNull(message = "Title can not be null") @NotEmpty(message = "Title can not be empty") @Size(min = 1, max = 50, message = "Title has to be between 1-50 characters") String getTitle() {
        return title;
    }

    public @NotNull(message = "Photos can not be null") @Size(max = 10, message = "You can add max 10 photos") List<String> getPhotos() {
        return photos;
    }

    public @NotNull(message = "Price per night can not be null") @Min(value = 1, message = "PricePerNight have to be minimum 1") @Max(value = 1000000, message = "PricePerNight have to be max 1 000 000") Double getPricePerNight() {
        return pricePerNight;
    }

    public @NotNull(message = "Address can not be null") @Valid Address getAddress() {
        return address;
    }

    public @NotNull(message = "Category can not be null") Category getCategory() {
        return category;
    }

    public @NotNull(message = "Amenities can not be null") @Valid List<Amenity> getAmenities() {
        return amenities;
    }

    public @NotNull(message = "Capacity can not be null") Integer getCapacity() {
        return capacity;
    }

    public @NotNull(message = "Periods can not be null") @Valid List<Period> getAvailablePeriods() {
        return availablePeriods;
    }

    public @NotNull(message = "Description can not be null") @NotEmpty(message = "Description can not be empty") @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters") String getDescription() {
        return description;
    }

    public String getPolicy() {
        return policy;
    }
}
