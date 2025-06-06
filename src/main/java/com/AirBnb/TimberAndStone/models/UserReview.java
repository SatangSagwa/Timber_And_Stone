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

@Document(collection="userreviews")
public class UserReview {

    @Id
    private String id;

    @DBRef
    @NotNull(message="fromHost can not be null")
    private User fromHost;

    @DBRef
    @NotNull(message="toUser can not be null")
    private User toUser;

    @DBRef
    @NotNull(message = "Booking can not be null")
    private Booking booking;

    @Min(value=1, message="Rating can not be below 1")
    @Max(value=5, message="Rating can not be above 5")
    private Integer rating;

    @NotNull(message = "Review can not be null")
    @Size(min=1, max=500, message="Review has to be between 1-500 characters")
    private String review;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;


    public UserReview() {
    }

    public String getId() {
        return id;
    }

    public @NotNull(message = "fromHost can not be null") User getFromHost() {
        return fromHost;
    }

    public void setFromHost(@NotNull(message = "fromHost can not be null") User fromHost) {
        this.fromHost = fromHost;
    }

    public @NotNull(message = "toUser can not be null") User getToUser() {
        return toUser;
    }

    public void setToUser(@NotNull(message = "toUser can not be null") User toUser) {
        this.toUser = toUser;
    }

    public @NotNull(message = "Booking can not be null") Booking getBooking() {
        return booking;
    }

    public void setBooking(@NotNull(message = "Booking can not be null") Booking booking) {
        this.booking = booking;
    }

    public @Min(value = 1, message = "Rating can not be below 1") @Max(value = 5, message = "Rating can not be above 5") Integer getRating() {
        return rating;
    }

    public void setRating(@Min(value = 1, message = "Rating can not be below 1") @Max(value = 5, message = "Rating can not be above 5") Integer rating) {
        this.rating = rating;
    }

    public @NotNull(message = "Review can not be null") @Size(min = 1, max = 500, message = "Review has to be between 1-500 characters") String getReview() {
        return review;
    }

    public void setReview(@NotNull(message = "Review can not be null") @Size(min = 1, max = 500, message = "Review has to be between 1-500 characters") String review) {
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
