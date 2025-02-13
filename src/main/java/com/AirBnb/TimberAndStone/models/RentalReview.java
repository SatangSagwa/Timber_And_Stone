package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rentalreviews")
public class RentalReview {

    @Id
    private String id;

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

    @Size(min=1, max=500, message="Review has to be between 1-500 characters")
    private String review;

    @NotNull(message="createdAt can not be null")
    private LocalDateTime createdAt;

    @NotNull(message="updatedAt can not be null")
    private LocalDateTime updatedAt;

    public RentalReview() {
    }

    public String getId() {
        return id;
    }


    public @NotNull(message = "User can not be null") User getFromUser() {
        return fromUser;
    }

    public void setFromUser(@NotNull(message = "User can not be null") User fromUser) {
        this.fromUser = fromUser;
    }

    public @NotNull(message = "Rental can not be null") Rental getToRental() {
        return toRental;
    }

    public void setToRental(@NotNull(message = "Rental can not be null") Rental toRental) {
        this.toRental = toRental;
    }

    @NotNull
    @Min(value = 1, message = "Rating can not be below 1")
    @Max(value = 5, message = "Rating can not be above 5")
    public int getRating() {
        return rating;
    }

    public void setRating(@NotNull @Min(value = 1, message = "Rating can not be below 1") @Max(value = 5, message = "Rating can not be above 5") int rating) {
        this.rating = rating;
    }

    public @Size(min = 1, max = 500, message = "Review has to be between 1-500 characters") String getReview() {
        return review;
    }

    public void setReview(@Size(min = 1, max = 500, message = "Review has to be between 1-500 characters") String review) {
        this.review = review;
    }

    public @NotNull(message = "createdAt can not be null") LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(message = "createdAt can not be null") LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull(message = "updatedAt can not be null") LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull(message = "updatedAt can not be null") LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
