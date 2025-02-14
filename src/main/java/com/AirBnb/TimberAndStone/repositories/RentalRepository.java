package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.models.Rental;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RentalRepository extends MongoRepository<Rental, String> {
    List<Rental> findByCategory(Category category);
}
