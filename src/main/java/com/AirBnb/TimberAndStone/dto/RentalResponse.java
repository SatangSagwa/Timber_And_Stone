package com.AirBnb.TimberAndStone.dto;

public class RentalResponse {

    private String message;
    private String title;


//--------------------------------------------- Constructors ------------------------------------------------------------


    public RentalResponse(String message, String title) {
        this.message = message;
        this.title = title;
    }

    public RentalResponse() {
    }



    //--------------------------------------------- Getter & Setters -------------------------------------------------------


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
