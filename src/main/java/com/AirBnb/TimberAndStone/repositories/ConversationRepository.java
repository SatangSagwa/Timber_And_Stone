package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversationRepository extends MongoRepository<Conversation, String> {

    Conversation findByToUserId(String id);

    List<Conversation> findByFromUserId(String id);
}
