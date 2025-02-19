package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepository extends MongoRepository<Conversation, String> {
}
