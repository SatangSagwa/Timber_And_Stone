package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
