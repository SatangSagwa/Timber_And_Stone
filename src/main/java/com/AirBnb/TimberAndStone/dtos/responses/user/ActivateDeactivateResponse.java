package com.AirBnb.TimberAndStone.dtos.responses.user;

public class ActivateDeactivateResponse {

    private String message;
    private String username;
    private Boolean isActive;

    public ActivateDeactivateResponse(String message, String username, Boolean isActive) {
        this.message = message;
        this.username = username;
        this.isActive = isActive;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
