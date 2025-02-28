package com.AirBnb.TimberAndStone.repositories;

import com.AirBnb.TimberAndStone.models.Booking;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByRentalId(String rentalId);
    // find by booking number should be swapped for findByBookingNumberAndUserAndRental since
    // booking number is not globally unique and may return
    // multiple results and throw IncorrectResultSizeDataAccessException.
    Booking findByBookingNumber(String bookingId);
    Optional<Booking> findByBookingNumberAndUserAndRental(String bookingNumber, User user, Rental rental);
}
