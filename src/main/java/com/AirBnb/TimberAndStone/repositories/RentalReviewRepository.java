package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.RentalReview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RentalReviewRepository extends MongoRepository<RentalReview, String> {
    List<RentalReview> findByUserId(String userId);
    List<RentalReview> findByRentalId(String rentalId);
}
