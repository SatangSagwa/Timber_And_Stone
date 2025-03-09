package com.AirBnb.TimberAndStone.dtos.requests.user;

import com.AirBnb.TimberAndStone.models.Address;
import com.AirBnb.TimberAndStone.models.Rental;

import java.util.List;

public class PatchUserRequest {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;
    private String profilePhoto;
    private List<Rental> favouriteRentals;

    public PatchUserRequest() {

    }

    public List<Rental> getFavouriteRentals() {
        return favouriteRentals;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
