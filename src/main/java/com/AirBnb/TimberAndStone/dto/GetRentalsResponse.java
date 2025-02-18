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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
