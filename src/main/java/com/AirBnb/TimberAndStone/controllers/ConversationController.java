package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.models.Conversation;
import com.AirBnb.TimberAndStone.services.ConversationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

 private final ConversationService conversationService;


    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }



    @PostMapping
    public ResponseEntity<Conversation> createConversation(@Valid @RequestBody Conversation conversation) {
        Conversation newConversation = conversationService.createConversation(conversation);
        return ResponseEntity.status(HttpStatus.CREATED).body(newConversation);
    }

    @GetMapping
    public ResponseEntity<List<Conversation>> getAllConversations() {
        List<Conversation> conversations = conversationService.getAllConversations();
        return ResponseEntity.status(HttpStatus.OK).body(conversations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conversation> getConversationById(@PathVariable String id) {
        Conversation conversation = conversationService.getConversationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(conversation);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Conversation> getConversationByUsername(@PathVariable String username) {
        Conversation conversation = conversationService.getConversationByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(conversation);
    }

    @GetMapping("/touser/{id}")
    public ResponseEntity<Conversation> getConversationByToUserId(@PathVariable String id) {
        Conversation conversation = conversationService.getConversationByToUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(conversation);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Conversation>> getMyConversations() {
        List<Conversation> conversations = conversationService.getMyConversations();
        return ResponseEntity.status(HttpStatus.OK).body(conversations);
    }

















}
