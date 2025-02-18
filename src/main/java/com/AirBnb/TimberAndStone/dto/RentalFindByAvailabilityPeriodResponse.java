package com.AirBnb.TimberAndStone.dto;

import java.time.Period;
import java.util.List;

public class RentalFindByAvailabilityPeriodResponse {
    private String title;
    private List<Period> periods;





    //--------------------------------------------- Constructors ------------------------------------------------------------

    public RentalFindByAvailabilityPeriodResponse() {
    }

    public RentalFindByAvailabilityPeriodResponse(String title, List<Period> periods) {
        this.title = title;
        this.periods = periods;
    }

    //--------------------------------------------- Getter & Setters -------------------------------------------------------


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }
}
