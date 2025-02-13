package com.AirBnb.TimberAndStone.models;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

    @NotNull
    @Min(value=1, message="Rating can not be below 1")
    @Max(value=5, message="Rating can not be above 5")
    private int rating;

    @NotNull(message="createdAt can not be null")
    private LocalDateTime createdAt;

    @NotNull(message="updatedAt can not be null")
    private LocalDateTime updatedAt;


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

    @NotNull
    @Min(value = 1, message = "Rating can not be below 1")
    @Max(value = 5, message = "Rating can not be above 5")
    public int getRating() {
        return rating;
    }

    public void setRating(@NotNull @Min(value = 1, message = "Rating can not be below 1") @Max(value = 5, message = "Rating can not be above 5") int rating) {
        this.rating = rating;
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
