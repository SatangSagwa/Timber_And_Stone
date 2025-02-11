package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "messages")
public class Message {

    @Id
    private String id;

    @NotNull(message = "toUser cant be null")
    @DBRef
    private User toUser;

    @NotNull(message = "fromUser cant be null")
    @DBRef
    private User fromUser;

    @NotNull(message = "Message cant be null")
    @NotEmpty(message = "Message cant be empty")
    @Max(value = 200)
    private String message;

    @NotNull(message = "MessageStatus cant be null")
    private MessageStatus messageStatus;

    @NotNull(message = "CreatedAt cannot be null")
    private Date createdAt;


//--------------------------------------------- Constructor ------------------------------------------------------------

    public Message() {
    }


//--------------------------------------------- Getter & Setters -------------------------------------------------------


    public String getId() {
        return id;
    }


    public @NotNull(message = "toUser cant be null") User getToUser() {
        return toUser;
    }

    public void setToUser(@NotNull(message = "toUser cant be null") User toUser) {
        this.toUser = toUser;
    }

    public @NotNull(message = "fromUser cant be null") User getFromUser() {
        return fromUser;
    }

    public void setFromUser(@NotNull(message = "fromUser cant be null") User fromUser) {
        this.fromUser = fromUser;
    }

    public @NotNull(message = "Message cant be null") @NotEmpty(message = "Message cant be empty") @Max(value = 200) String getMessage() {
        return message;
    }

    public void setMessage(@NotNull(message = "Message cant be null") @NotEmpty(message = "Message cant be empty") @Max(value = 200) String message) {
        this.message = message;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public @NotNull(message = "CreatedAt cannot be null") Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(message = "CreatedAt cannot be null") Date createdAt) {
        this.createdAt = createdAt;
    }
}
