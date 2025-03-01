package com.AirBnb.TimberAndStone.responses.rental.TOBEREMOVEDIFWORKS;

import com.AirBnb.TimberAndStone.models.Category;

public class RentalFindByCategoryResponse {

    private String title;
    private Category category;

    //--------------------------------------------- Constructors ------------------------------------------------------------

    public RentalFindByCategoryResponse() {
    }

    public RentalFindByCategoryResponse(String title, Category category) {
        this.title = title;
        this.category = category;
    }

    //--------------------------------------------- Getter & Setters -------------------------------------------------------


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
