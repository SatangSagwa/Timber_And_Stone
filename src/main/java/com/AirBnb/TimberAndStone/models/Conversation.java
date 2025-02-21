package com.AirBnb.TimberAndStone.models;

import jakarta.validation.Valid;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "conversations")
public class Conversation {

    @Id
    private String id;

    @DBRef
    private User toUser;

    @DBRef
    private User fromUser;

    // https://chatgpt.com/share/67b75b0d-e0d8-800b-96c6-0894c9a6c0b8
    @Valid
    private List<Message> messages;

    @CreatedDate
    private LocalDateTime createdAt;

    //--------------------------------------------- Constructor ------------------------------------------------------------

    public Conversation() {
    }

    public Conversation(String id, User toUser, User fromUser, List<Message> messages, LocalDateTime createdAt) {
        this.id = id;
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.messages = messages;
        this.createdAt = createdAt;
    }
//--------------------------------------------- Getter & Setters -------------------------------------------------------


    public String getId() {
        return id;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public @Valid List<Message> getMessages() {
        return messages;
    }

    public void setMessages(@Valid List<Message> messages) {
        this.messages = messages;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
