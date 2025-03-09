package com.AirBnb.TimberAndStone.dtos.responses.user;

import com.AirBnb.TimberAndStone.models.Address;
import com.AirBnb.TimberAndStone.models.Rating;
import com.AirBnb.TimberAndStone.models.Rental;

import java.util.List;

public class PatchUserResponse {
    private String message;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;
    private Rating rating;
    private String profilePhoto;
    private List<Rental> favouriteRentals;

    public PatchUserResponse() {

    }
    public PatchUserResponse(String message, String email, String username, String firstName, String lastName, String phoneNumber, Address address, Rating rating, String profilePhoto, List<Rental> favouriteRentals) {
        this.message = message;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.rating = rating;
        this.profilePhoto = profilePhoto;
        this.favouriteRentals = favouriteRentals;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public List<Rental> getFavouriteRentals() {
        return favouriteRentals;
    }

    public void setFavouriteRentals(List<Rental> favouriteRentals) {
        this.favouriteRentals = favouriteRentals;
    }
}
