package com.AirBnb.TimberAndStone.dto;

public class RentalResponse {

    private String title;

    private String message;
















//--------------------------------------------- Constructors ------------------------------------------------------------

    public RentalResponse(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public RentalResponse() {
    }

    //--------------------------------------------- Getter & Setters -------------------------------------------------------


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
