package com.AirBnb.TimberAndStone.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

public class Rental {
    private String title;
    private List<String> photos;
    private double pricePerNight;
    //private Rating rating;

    @DBRef
    private User host;

    private Address address;

    private Category category;

    //private List<Amenity> amenities;

    private int capacity;

    private List<Period> periods;

    private String description;

    private String policy;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
