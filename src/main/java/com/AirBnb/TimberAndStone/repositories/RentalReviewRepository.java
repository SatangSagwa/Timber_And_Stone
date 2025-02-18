package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.RentalReview;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RentalReviewRepository extends MongoRepository<RentalReview, String> {
}
