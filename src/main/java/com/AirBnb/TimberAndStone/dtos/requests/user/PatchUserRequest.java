package com.AirBnb.TimberAndStone.dtos.requests.user;

import com.AirBnb.TimberAndStone.models.Address;
import com.AirBnb.TimberAndStone.models.Rental;
import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

public class PatchUserRequest {
    @Email(message = "Incorrect email format")
    @Indexed(unique = true)
    @NotNull
    @NotEmpty
    private String email;

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

    @NotNull(message = "Address cannot be null")
    private Address address;

    @NotEmpty(message = "Profile photo cant be empty")
    @NotNull(message = "Profile photo cant be null")
    @Max(value = 300)
    private String profilePhoto; // ifall inget photo uppladdas vid skapande av konto så väljs ett default photo

    @Max(value = 10, message = "you can max have 10 favorite rentals")
    private List<Rental> favouriteRentals;

    public PatchUserRequest() {

    }

    public @Max(value = 10, message = "you can max have 10 favorite rentals") List<Rental> getFavouriteRentals() {
        return favouriteRentals;
    }

    public @NotEmpty(message = "Profile photo cant be empty") @NotNull(message = "Profile photo cant be null") @Max(value = 300) String getProfilePhoto() {
        return profilePhoto;
    }

    public @NotNull(message = "Address cannot be null") Address getAddress() {
        return address;
    }

    public @NotEmpty(message = "phoneNumber cannot be empty") @NotNull(message = "phoneNumber cannot be null") @Size(max = 30) String getPhoneNumber() {
        return phoneNumber;
    }

    public @NotEmpty(message = "Lastname cannot be empty") @NotNull(message = "LastName cannot be null") @Size(max = 30) String getLastName() {
        return lastName;
    }

    public @NotEmpty(message = "Firstname cannot be empty") @NotNull(message = "Firstname cannot be null") @Size(max = 30) String getFirstName() {
        return firstName;
    }

    public @NotEmpty(message = "Username cannot be empty") @NotNull(message = "Username cannot be null") @Size(max = 30) String getUsername() {
        return username;
    }

    public @Email(message = "Incorrect email format") @NotNull @NotEmpty String getEmail() {
        return email;
    }

}
