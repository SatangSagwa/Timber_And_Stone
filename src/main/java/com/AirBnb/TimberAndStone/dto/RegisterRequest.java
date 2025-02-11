package com.AirBnb.TimberAndStone.dto;


import com.AirBnb.TimberAndStone.models.Role;
import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Set;

public class RegisterRequest {
    @Email(message = "Incorrect email format")
    @Indexed(unique = true)
    @NotNull
    @NotEmpty
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" + ".*$", message = "Password must be at least 8 characters long and contain at least " + "one uppercase letter, one number, and one special character")
    private String password;

    @Indexed(unique = true)
    @NotEmpty(message = "Username cannot be empty")
    @NotNull(message = "Username cannot be null")
    @Size(max = 30)
    private String username;

    @NotEmpty(message = "Firstname cannot be empty")
    @NotNull(message = "Firstname cannot be null")
    @Size(max = 30)
    private String firstName;

    @NotEmpty(message = "Lastname cannot be empty")
    @NotNull(message = "LastName cannot be null")
    @Size(max = 30)
    private String lastName;

    @NotEmpty(message = "phoneNumber cannot be empty")
    @NotNull(message = "phoneNumber cannot be null")
    @Size(max = 30)
    private String phoneNumber;

    //@NotNull(message = "Address cannot be null")
    //private Address address;

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
    private Double latitude;

    @NotNull(message = "Longitude can not be null")
    private Double longitude;

    @NotEmpty(message = "Profile photo cant be empty")
    //@Max(value = 300)
    private String profilePhoto; // ifall inget photo uppladdas vid skapande av konto så väljs ett default photo

    private Set<Role> roles;

    public RegisterRequest(String email, String password, String username, String firstName, String lastName, String phoneNumber, String country, String city, String postalCode, String streetName, String streetNumber, Double latitude, Double longitude, String profilePhoto, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.profilePhoto = profilePhoto;
        this.roles = roles;
    }

    public @Email(message = "Incorrect email format") @NotNull @NotEmpty String getEmail() {
        return email;
    }

    public @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" + ".*$", message = "Password must be at least 8 characters long and contain at least " + "one uppercase letter, one number, and one special character") String getPassword() {
        return password;
    }

    public @NotEmpty(message = "Username cannot be empty") @NotNull(message = "Username cannot be null") @Size(max = 30) String getUsername() {
        return username;
    }

    public @NotEmpty(message = "Firstname cannot be empty") @NotNull(message = "Firstname cannot be null") @Size(max = 30) String getFirstName() {
        return firstName;
    }

    public @NotEmpty(message = "Lastname cannot be empty") @NotNull(message = "LastName cannot be null") @Size(max = 30) String getLastName() {
        return lastName;
    }

    public @NotEmpty(message = "phoneNumber cannot be empty") @NotNull(message = "phoneNumber cannot be null") @Size(max = 30) String getPhoneNumber() {
        return phoneNumber;
    }

    public @NotNull(message = "Country can not be null") @NotEmpty(message = "Country can not be empty") String getCountry() {
        return country;
    }

    public @NotNull(message = "City can not be null") @NotEmpty(message = "City can not be empty") String getCity() {
        return city;
    }

    public @NotNull(message = "Postal code can not be null") @NotEmpty(message = "Postal code can not be empty") String getPostalCode() {
        return postalCode;
    }

    public @NotNull(message = "Street name can not be null") @NotEmpty(message = "Street name can not be empty") String getStreetName() {
        return streetName;
    }

    public @NotNull(message = "Street number can not be null") @NotEmpty(message = "Street number can not be empty") String getStreetNumber() {
        return streetNumber;
    }

    public @NotNull(message = "Latitude can not be null") Double getLatitude() {
        return latitude;
    }

    public @NotNull(message = "Longitude can not be null") Double getLongitude() {
        return longitude;
    }

    public @NotEmpty(message = "Profile photo cant be empty") String getProfilePhoto() {
        return profilePhoto;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    /*
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Set<Role> roles;

    public RegisterRequest(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

     */
}
