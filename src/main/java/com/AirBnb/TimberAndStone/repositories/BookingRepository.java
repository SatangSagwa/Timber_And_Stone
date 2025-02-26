package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByRentalId(String rentalId);
    Booking findByBookingNumber(String bookingId);
}
