package com.AirBnb.TimberAndStone.models;

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

    private List<Message> messages;

    @CreatedDate
    private LocalDateTime createdAt;

}
