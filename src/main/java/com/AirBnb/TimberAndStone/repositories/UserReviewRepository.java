package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.UserReview;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserReviewRepository extends MongoRepository<UserReview, String> {
}
