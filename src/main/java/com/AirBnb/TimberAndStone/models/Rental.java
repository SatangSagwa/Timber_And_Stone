package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

public class Rental {

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
}
