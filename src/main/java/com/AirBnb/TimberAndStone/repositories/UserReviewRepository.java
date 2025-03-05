package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.UserReview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserReviewRepository extends MongoRepository<UserReview, String> {
    Optional<UserReview> findByToUserId(String id);
}
