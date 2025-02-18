package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.User;
import com.AirBnb.TimberAndStone.models.UserReview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserReviewRepository extends MongoRepository<UserReview, String> {
    List<UserReview> getUserReviewBytoUser(User toUser);
    List<UserReview> getUserReviewByFromHost(User fromHost);

}
