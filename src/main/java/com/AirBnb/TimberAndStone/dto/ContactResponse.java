package com.AirBnb.TimberAndStone.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContactResponse {

    private String message;

    @NotEmpty(message = "Username cannot be empty")
    @NotNull(message = "Username cannot be null")
    @Size(max = 30)
    private String username;

    @NotNull
    @NotEmpty
    private String email;

    @NotEmpty(message = "phoneNumber cannot be empty")
    @NotNull(message = "phoneNumber cannot be null")
    @Size(max = 30)
    private String phoneNumber;

    public ContactResponse(String message ,String username, String phoneNumber, String email) {
        this.message = message;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public @NotEmpty(message = "Username cannot be empty") @NotNull(message = "Username cannot be null") @Size(max = 30) String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username cannot be empty") @NotNull(message = "Username cannot be null") @Size(max = 30) String username) {
        this.username = username;
    }

    public @NotNull @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @NotEmpty String email) {
        this.email = email;
    }

    public @NotEmpty(message = "phoneNumber cannot be empty") @NotNull(message = "phoneNumber cannot be null") @Size(max = 30) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotEmpty(message = "phoneNumber cannot be empty") @NotNull(message = "phoneNumber cannot be null") @Size(max = 30) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
