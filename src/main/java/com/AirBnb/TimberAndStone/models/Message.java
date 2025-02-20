package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

public class Message {

    @NotNull(message = "Message cant be null")
    @NotEmpty(message = "Message cant be empty")
    @Max(value = 200)
    private String message;

    @NotNull(message = "MessageStatus cant be null")
    private MessageStatus messageStatus;

    @CreatedDate
    private LocalDateTime createdAt;


//--------------------------------------------- Constructor ------------------------------------------------------------

    public Message() {
    }


//--------------------------------------------- Getter & Setters -------------------------------------------------------


    public @NotNull(message = "Message cant be null") @NotEmpty(message = "Message cant be empty") @Max(value = 200) String getMessage() {
        return message;
    }

    public void setMessage(@NotNull(message = "Message cant be null") @NotEmpty(message = "Message cant be empty") @Max(value = 200) String message) {
        this.message = message;
    }

    public @NotNull(message = "MessageStatus cant be null") MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(@NotNull(message = "MessageStatus cant be null") MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
