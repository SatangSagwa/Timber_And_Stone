package com.AirBnb.TimberAndStone.dto;

import com.AirBnb.TimberAndStone.models.Category;

public class GetRentalsResponse {
    private String title;
    private Category category;
    private Integer capacity;
    private Double pricePerNight;
    private String country;
    private String city;
    private Double averageRating;

    public GetRentalsResponse() {
    }

    public GetRentalsResponse(String title, Category category, Integer capacity, Double pricePerNight, String country, String city, Double averageRating) {
        this.title = title;
        this.category = category;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.country = country;
        this.city = city;
        this.averageRating = averageRating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
