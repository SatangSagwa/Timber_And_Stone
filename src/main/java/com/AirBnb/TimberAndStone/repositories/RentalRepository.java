package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.models.Rental;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RentalRepository extends MongoRepository<Rental, String> {
    List<Rental> findByCategory(Category category);

    // https://chatgpt.com/share/67b33e5b-0968-800b-bb00-ba09744d98fb
    List<Rental> findByRatingAverageRatingGreaterThanEqualAndRatingNumberOfRatingsGreaterThanEqual(Double avgRating, Integer numberofRatings);
    List<Rental> findByPricePerNightBetween(Double minPrice, Double maxPrice);
}
