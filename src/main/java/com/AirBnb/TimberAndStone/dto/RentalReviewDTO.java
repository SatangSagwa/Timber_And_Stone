package com.AirBnb.TimberAndStone.dto;

import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.models.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class RentalReviewDTO {
    @DBRef
    @NotNull(message="User can not be null")
    private User fromUser;

    @DBRef
    @NotNull(message="Rental can not be null")
    private Rental toRental;

    @NotNull
    @Min(value=1, message="Rating can not be below 1")
    @Max(value=5,message="Rating can not be above 5")
    private int rating;

    @Size(max=500, message="Review can max be 500 characters")
    private String review;

    public RentalReviewDTO() {
    }

    public RentalReviewDTO(User fromUser, Rental toRental, int rating, String review) {
        this.fromUser = fromUser;
        this.toRental = toRental;
        this.rating = rating;
        this.review = review;
    }
    // --------------------- Getters ------------------------------------------------
    public @NotNull(message = "User can not be null") User getFromUser() {
        return fromUser;
    }

    public @NotNull(message = "Rental can not be null") Rental getToRental() {
        return toRental;
    }

    @NotNull
    @Min(value = 1, message = "Rating can not be below 1")
    @Max(value = 5, message = "Rating can not be above 5")
    public int getRating() {
        return rating;
    }

    public @Size(max = 500, message = "Review can max be 500 characters") String getReview() {
        return review;
    }

}
