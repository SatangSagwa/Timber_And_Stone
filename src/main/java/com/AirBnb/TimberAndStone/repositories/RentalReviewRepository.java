package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.RentalReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RentalReviewRepository extends MongoRepository<RentalReview, String> {
    List<RentalReview> findByToRentalId(String id);
}
