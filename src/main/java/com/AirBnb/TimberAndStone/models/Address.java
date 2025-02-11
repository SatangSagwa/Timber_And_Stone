package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Adress {
    private final long maxLatitude = 90L;
    private final long minLatitude = -90L;

    private final long maxLongitude = 180L;
    private final long minLongitude = -180L;

    @NotNull(message = "Country can not be null")
    @NotEmpty(message = "Country can not be empty")
    private String country;

    @NotNull(message = "City can not be null")
    @NotEmpty(message = "City can not be empty")
    private String city;

    @NotNull(message = "Postal code can not be null")
    @NotEmpty(message = "Postal code can not be empty")
    private String postalCode;

    @NotNull(message = "Street name can not be null")
    @NotEmpty(message = "Street name can not be empty")
    private String streetName;

    @NotNull(message = "Street number can not be null")
    @NotEmpty(message = "Street number can not be empty")
    private String streetNumber;

    //-- Latitude and longitude in decimal degrees format (ex. 41.40338, 2.17403)
    @NotNull(message = "Latitude can not be null")
    @Min(value = minLatitude, message = "Latitude has to be between -90 & +90")
    @Max(value = maxLatitude, message = "Latitude has to be between -90 & +90")
    private Double latitude;

    @NotNull(message = "Longitude can not be null")
    @Min(value = minLongitude, message = "Latitude has to be between -180 & +180")
    @Max(value = maxLongitude, message = "Latitude has to be between -180 & +180")
    private Double longitude;

    //--------------------------------------------- Constructor ------------------------------------------------------------

    public Adress() {
    }

    //--------------------------------------------- Getter & Setters -------------------------------------------------------

    public @NotNull(message = "Country can not be null") @NotEmpty(message = "Country can not be empty") String getCountry() {
        return country;
    }

    public void setCountry(@NotNull(message = "Country can not be null") @NotEmpty(message = "Country can not be empty") String country) {
        this.country = country;
    }

    public @NotNull(message = "City can not be null") @NotEmpty(message = "City can not be empty") String getCity() {
        return city;
    }

    public void setCity(@NotNull(message = "City can not be null") @NotEmpty(message = "City can not be empty") String city) {
        this.city = city;
    }

    public @NotNull(message = "Postal code can not be null") @NotEmpty(message = "Postal code can not be empty") String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(@NotNull(message = "Postal code can not be null") @NotEmpty(message = "Postal code can not be empty") String postalCode) {
        this.postalCode = postalCode;
    }

    public @NotNull(message = "Street name can not be null") @NotEmpty(message = "Street name can not be empty") String getStreetName() {
        return streetName;
    }

    public void setStreetName(@NotNull(message = "Street name can not be null") @NotEmpty(message = "Street name can not be empty") String streetName) {
        this.streetName = streetName;
    }

    public @NotNull(message = "Street number can not be null") @NotEmpty(message = "Street number can not be empty") String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(@NotNull(message = "Street number can not be null") @NotEmpty(message = "Street number can not be empty") String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public @NotNull(message = "Latitude can not be null") @Min(value = minLatitude, message = "Latitude has to be between -90 & +90") @Max(value = maxLatitude, message = "Latitude has to be between -90 & +90") Double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NotNull(message = "Latitude can not be null") @Min(value = minLatitude, message = "Latitude has to be between -90 & +90") @Max(value = maxLatitude, message = "Latitude has to be between -90 & +90") Double latitude) {
        this.latitude = latitude;
    }

    public @NotNull(message = "Longitude can not be null") @Min(value = minLongitude, message = "Latitude has to be between -180 & +180") @Max(value = maxLongitude, message = "Latitude has to be between -180 & +180") Double getLongitude() {
        return longitude;
    }

    public void setLongitude(@NotNull(message = "Longitude can not be null") @Min(value = minLongitude, message = "Latitude has to be between -180 & +180") @Max(value = maxLongitude, message = "Latitude has to be between -180 & +180") Double longitude) {
        this.longitude = longitude;
    }
}