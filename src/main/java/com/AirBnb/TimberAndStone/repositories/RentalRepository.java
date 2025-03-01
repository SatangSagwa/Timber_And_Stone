package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.models.Rental;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RentalRepository extends MongoRepository<Rental, String> {
    List<Rental> findByCategory(Category category);

    // https://chatgpt.com/share/67bf0c89-4cac-800b-9d3e-bcd5eeb0e944
    List<Rental> findByRatingAverageRatingGreaterThanEqualAndRatingNumberOfRatingsGreaterThanEqual(Double avgRating, Integer numberOfRatings);

    // https://chatgpt.com/share/67bf07f3-8c18-800b-bcce-c51086343cfd
    @Query("{'pricePerNight': { $gte: ?0, $lte: ?1 }}")
    List<Rental> findByPricePerNightBetweenInclusive(Double minPrice, Double maxPrice);

    List<Rental> findByRatingAverageRating(Double averageRating);
    List<Rental> findByHostId(String hostId);

}
