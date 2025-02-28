package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

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


    @Min(value=1, message="Rating can not be below 1")
    @Max(value=5,message="Rating can not be above 5")
    private Integer rating;

    @Size(max=500, message="Review can max be between 500 characters")
    private String review;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

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

    public @Min(value = 1, message = "Rating can not be below 1") @Max(value = 5, message = "Rating can not be above 5") Integer getRating() {
        return rating;
    }

    public void setRating(@Min(value = 1, message = "Rating can not be below 1") @Max(value = 5, message = "Rating can not be above 5") Integer rating) {
        this.rating = rating;
    }

    public @Size(max = 500, message = "Review can max be between 500 characters") String getReview() {
        return review;
    }

    public void setReview(@Size(max = 500, message = "Review can max be between 500 characters") String review) {
        this.review = review;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
