package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class Address {

    //@NotNull(message = "Country can not be null")
    @NotEmpty(message = "Country can not be empty")
    private String country;

    //@NotNull(message = "City can not be null")
    @NotEmpty(message = "City can not be empty")
    private String city;

    //@NotNull(message = "Postal code can not be null")
    @NotEmpty(message = "Postal code can not be empty")
    private String postalCode;

    //@NotNull(message = "Street name can not be null")
    @NotEmpty(message = "Street name can not be empty")
    private String streetName;

    //@NotNull(message = "Street number can not be null")
    @NotEmpty(message = "Street number can not be empty")
    private String streetNumber;

    //-- Latitude and longitude in decimal degrees format (ex. 41.40338, 2.17403)
    @NotNull(message = "Latitude can not be null")
    @Min(value = -90L, message = "Latitude has to be between -90 & +90")
    @Max(value = 90L, message = "Latitude has to be between -90 & +90")
    private Double latitude;

    @NotNull(message = "Longitude can not be null")
    @Min(value = -180L, message = "Latitude has to be between -180 & +180")
    @Max(value = 180L, message = "Latitude has to be between -180 & +180")
    private Double longitude;

    //--------------------------------------------- Constructor ------------------------------------------------------------

    public Address() {
    }

    public Address(String country, String city, String postalCode, String streetName, String streetNumber, Double latitude, Double longitude) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //--------------------------------------------- Getter & Setters -------------------------------------------------------


    public @NotEmpty(message = "Country can not be empty") String getCountry() {
        return country;
    }

    public void setCountry(@NotEmpty(message = "Country can not be empty") String country) {
        this.country = country;
    }

    public @NotEmpty(message = "City can not be empty") String getCity() {
        return city;
    }

    public void setCity(@NotEmpty(message = "City can not be empty") String city) {
        this.city = city;
    }

    public @NotEmpty(message = "Postal code can not be empty") String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(@NotEmpty(message = "Postal code can not be empty") String postalCode) {
        this.postalCode = postalCode;
    }

    public @NotEmpty(message = "Street name can not be empty") String getStreetName() {
        return streetName;
    }

    public void setStreetName(@NotEmpty(message = "Street name can not be empty") String streetName) {
        this.streetName = streetName;
    }

    public @NotEmpty(message = "Street number can not be empty") String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(@NotEmpty(message = "Street number can not be empty") String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public @NotNull(message = "Latitude can not be empty") @Min(value = -90L, message = "Latitude has to be between -90 & +90") @Max(value = 90L, message = "Latitude has to be between -90 & +90") Double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NotNull(message = "Latitude can not be empty") @Min(value = -90L, message = "Latitude has to be between -90 & +90") @Max(value = 90L, message = "Latitude has to be between -90 & +90") Double latitude) {
        this.latitude = latitude;
    }

    public @NotNull(message = "Longitude can not be empty") @Min(value = -180L, message = "Latitude has to be between -180 & +180") @Max(value = 180L, message = "Latitude has to be between -180 & +180") Double getLongitude() {
        return longitude;
    }

    public void setLongitude(@NotNull(message = "Longitude can not be empty") @Min(value = -180L, message = "Latitude has to be between -180 & +180") @Max(value = 180L, message = "Latitude has to be between -180 & +180") Double longitude) {
        this.longitude = longitude;
    }
}