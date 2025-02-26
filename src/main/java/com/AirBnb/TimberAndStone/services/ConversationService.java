package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.models.Conversation;
import com.AirBnb.TimberAndStone.models.Message;
import com.AirBnb.TimberAndStone.models.User;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.ConversationRepository;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final PeriodService periodService;
    private final RentalService rentalService;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public ConversationService(ConversationRepository conversationRepository, BookingRepository bookingRepository, UserService userService, PeriodService periodService, RentalService rentalService, UserRepository userRepository, RentalRepository rentalRepository) {
        this.conversationRepository = conversationRepository;
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.periodService = periodService;
        this.rentalService = rentalService;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }


    public Conversation createConversation(Conversation conversation) {
        // implementerar validering senare & felhantering sen
        Conversation newConversation = new Conversation();
        Message message = new Message();
        //Set fromUser to authorized user.
        newConversation.setFromUser(userService.getAuthenticated());
        // Set fields
        newConversation.setToUser(conversation.getToUser());
        for (Message m : conversation.getMessages()) {
            if (m.getCreatedAt() == null) {
                m.setCreatedAt(LocalDateTime.now());
            }
        }
        newConversation.setMessages(conversation.getMessages());
        conversationRepository.save(newConversation);
        return newConversation;
    }

    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    public Conversation getConversationById(String id) {
        return conversationRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Conversation not found"));
    }

    public Conversation getConversationByUsername(String username){
        User user = userService.findByUsername(username);
        return conversationRepository.findByToUserId(user.getId());
    }

    public List<Conversation> getMyConversations() {
        User user = userService.getAuthenticated();
        return conversationRepository.findByFromUserId(user.getId());
    }

    public Conversation getConversationByToUserId(String id) {
        return  conversationRepository.findByToUserId(id);
    }


    //------------------------------------------HELP METHODS----------------------------------------------------






}
