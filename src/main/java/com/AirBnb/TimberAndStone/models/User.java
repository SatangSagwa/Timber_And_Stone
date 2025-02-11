package com.AirBnb.TimberAndStone.models;


import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "users")
public class User {
    @Id
    private String id;

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


    @NotNull(message = "isActive cannot be null")
    private Boolean isActive;


    @NotNull(message = "CreatedAt cannot be null")
    private Date createdAt;

    @NotNull(message = "UpdatedAt cannot be null")
    private Date updatedAt;

    @NotEmpty(message = "Profile photo cant be empty")
    @NotNull(message = "Profile photo cant be null")
    @Max(value = 300)
    private String profilePhoto; // ifall inget photo uppladdas vid skapande av konto så väljs ett default photo

    @Max(value = 10, message = "you can max have 10 favorite rentals")
    //private List<Rental> favouriteRentals;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" + ".*$", message = "Password must be at least 8 characters long and contain at least " + "one uppercase letter, one number, and one special character")
    private String password;

    @NotNull(message = "Roles cannot be null")
    private Set<Role> roles;



//--------------------------------------------- Constructor ------------------------------------------------------------

    public User() {
    }

//--------------------------------------------- Getter & Setters -------------------------------------------------------


    public String getId() {
        return id;
    }


    public @NotNull(message = "Address cannot be null") Address getAddress() {
        return address;
    }

    public void setAddress(@NotNull(message = "Address cannot be null") Address address) {
        this.address = address;
    }

    public @Email(message = "Incorrect email format") @NotNull @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Incorrect email format") @NotNull @NotEmpty String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Username cannot be empty") @NotNull(message = "Username cannot be null") @Size(max = 30) String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username cannot be empty") @NotNull(message = "Username cannot be null") @Size(max = 30) String username) {
        this.username = username;
    }

    public @NotEmpty(message = "Firstname cannot be empty") @NotNull(message = "Firstname cannot be null") @Size(max = 30) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotEmpty(message = "Firstname cannot be empty") @NotNull(message = "Firstname cannot be null") @Size(max = 30) String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty(message = "Lastname cannot be empty") @NotNull(message = "LastName cannot be null") @Size(max = 30) String getLastName() {
        return lastName;
    }

    public void setLastName(@NotEmpty(message = "Lastname cannot be empty") @NotNull(message = "LastName cannot be null") @Size(max = 30) String lastName) {
        this.lastName = lastName;
    }

    public @NotEmpty(message = "phoneNumber cannot be empty") @NotNull(message = "phoneNumber cannot be null") @Size(max = 30) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotEmpty(message = "phoneNumber cannot be empty") @NotNull(message = "phoneNumber cannot be null") @Size(max = 30) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotNull(message = "isActive cannot be null") Boolean getActive() {
        return isActive;
    }

    public void setActive(@NotNull(message = "isActive cannot be null") Boolean active) {
        isActive = active;
    }

    public @NotNull(message = "CreatedAt cannot be null") Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(message = "CreatedAt cannot be null") Date createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull(message = "UpdatedAt cannot be null") Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull(message = "UpdatedAt cannot be null") Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public @NotEmpty(message = "Profile photo cant be empty") @NotNull(message = "Profile photo cant be null") @Size(max = 300) String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(@NotEmpty(message = "Profile photo cant be empty") @NotNull(message = "Profile photo cant be null") @Size(max = 300) String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public @Max(value = 10, message = "you can max have 10 favorite rentals") @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" + ".*$", message = "Password must be at least 8 characters long and contain at least " + "one uppercase letter, one number, and one special character") String getPassword() {
        return password;
    }

    public void setPassword(@Max(value = 10, message = "you can max have 10 favorite rentals") @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" + ".*$", message = "Password must be at least 8 characters long and contain at least " + "one uppercase letter, one number, and one special character") String password) {
        this.password = password;
    }

    public @NotNull(message = "Roles cannot be null") Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(@NotNull(message = "Roles cannot be null") Set<Role> roles) {
        this.roles = roles;
    }
}

