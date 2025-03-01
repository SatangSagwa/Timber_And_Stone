package com.AirBnb.TimberAndStone.responses.rental.TOBEREMOVEDIFWORKS;

public class RentalFindByPricePerNightRangeResponse {

    private String title;
    private Double pricePerNight;


    //--------------------------------------------- Constructors ------------------------------------------------------------

    public RentalFindByPricePerNightRangeResponse() {
    }

    public RentalFindByPricePerNightRangeResponse(String title, Double pricePerNight) {
        this.title = title;
        this.pricePerNight = pricePerNight;
    }
    //--------------------------------------------- Getter & Setters -------------------------------------------------------


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
}
