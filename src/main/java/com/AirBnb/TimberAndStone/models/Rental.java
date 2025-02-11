package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "rentals")
public class Rental {

    @Id
    private String id;

    @NotNull(message = "Title can not be null")
    @NotEmpty(message = "Title can not be empty")
    @Size(min = 1, max = 50, message = "Title has to be between 1-50 characters")
    private String title;

    @NotNull(message = "Photos can not be null")
    @Max(value = 10, message = "You can add max 10 photos")
    private List<String> photos;

    @NotNull(message = "Price per night can not be null")
    @Size(min = 1, max = 1000000, message = "pricePerNight has to be between 1-1.000.000")
    private double pricePerNight;

    //@NotNull(message = "Rating can not be null")
    //private Rating rating;

    @DBRef
    @NotNull(message = "Host can not be null")
    private User host;

    @NotNull(message = "Address can not be null")
    private Address address;

    @NotNull(message = "Category can not be null")
    private Category category;

    //@NotNull(message = "Amenities can not be null")
    //private List<Amenity> amenities;

    @NotNull(message = "Capacity can not be null")
    private int capacity;

    @NotNull(message = "Periods can not be null")
    private List<Period> availablePeriods;

    @NotNull(message = "Description can not be null")
    @NotEmpty(message = "Description can not be empty")
    @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters")
    private String description;

    @NotNull(message = "Policy can not be null")
    private String policy;

    @NotNull(message = "createdAt can not be null")
    private LocalDate createdAt;

    @NotNull(message = "createdAt can not be null")
    private LocalDate updatedAt;

    public Rental() {
    }

    public @NotNull(message = "Title can not be null") @NotEmpty(message = "Title can not be empty") @Size(min = 1, max = 50, message = "Title has to be between 1-50 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "Title can not be null") @NotEmpty(message = "Title can not be empty") @Size(min = 1, max = 50, message = "Title has to be between 1-50 characters") String title) {
        this.title = title;
    }

    public @NotNull(message = "Photos can not be null") @Max(value = 10, message = "You can add max 10 photos") List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(@NotNull(message = "Photos can not be null") @Max(value = 10, message = "You can add max 10 photos") List<String> photos) {
        this.photos = photos;
    }

    @NotNull(message = "Price per night can not be null")
    @Size(min = 1, max = 1000000, message = "pricePerNight has to be between 1-1.000.000")
    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(@NotNull(message = "Price per night can not be null") @Size(min = 1, max = 1000000, message = "pricePerNight has to be between 1-1.000.000") double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    /*
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

     */

    public @NotNull(message = "Host can not be null") User getHost() {
        return host;
    }

    public void setHost(@NotNull(message = "Host can not be null") User host) {
        this.host = host;
    }

    public @NotNull(message = "Address can not be null") Address getAddress() {
        return address;
    }

    public void setAddress(@NotNull(message = "Address can not be null") Address address) {
        this.address = address;
    }

    public @NotNull(message = "Category can not be null") Category getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Category can not be null") Category category) {
        this.category = category;
    }

    /*
    public @NotNull(message = "Amenities can not be null") List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(@NotNull(message = "Amenities can not be null") List<Amenity> amenities) {
        this.amenities = amenities;
    }
     */

    @NotNull(message = "Capacity can not be null")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull(message = "Capacity can not be null") int capacity) {
        this.capacity = capacity;
    }

    public @NotNull(message = "Periods can not be null") List<Period> getAvailablePeriods() {
        return availablePeriods;
    }

    public void setAvailablePeriods(@NotNull(message = "Periods can not be null") List<Period> availablePeriods) {
        this.availablePeriods = availablePeriods;
    }

    public @NotNull(message = "Description can not be null") @NotEmpty(message = "Description can not be empty") @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description can not be null") @NotEmpty(message = "Description can not be empty") @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters") String description) {
        this.description = description;
    }

    public @NotNull(message = "Policy can not be null") String getPolicy() {
        return policy;
    }

    public void setPolicy(@NotNull(message = "Policy can not be null") String policy) {
        this.policy = policy;
    }

    public @NotNull(message = "createdAt can not be null") LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(message = "createdAt can not be null") LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull(message = "createdAt can not be null") LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull(message = "createdAt can not be null") LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
