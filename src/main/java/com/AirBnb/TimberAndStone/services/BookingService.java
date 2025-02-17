package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.dto.AllBookingsResponse;
import com.AirBnb.TimberAndStone.dto.BookingRequest;
import com.AirBnb.TimberAndStone.dto.BookingResponse;
import com.AirBnb.TimberAndStone.dto.PostBookingResponse;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.*;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final PeriodService periodService;
    private final RentalService rentalService;

    public BookingService(BookingRepository bookingRepository, PeriodService periodService, UserService userService, RentalService rentalService) {
        this.bookingRepository = bookingRepository;
        this.periodService = periodService;
        this.userService = userService;
        this.rentalService = rentalService;
    }

    public PostBookingResponse createBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();

        //Set user to authorized user.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("User is not authenticated");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        booking.setUser(user);

        //Find and set rental
        Rental rental = rentalService.getRentalById(bookingRequest.getRental().getId());
        booking.setRental(rental);

        //DTO values
        Period period = new Period();
        period.setStartDate(bookingRequest.getStartDate());
        period.setEndDate(bookingRequest.getEndDate());
        booking.setPeriod(period);
        booking.setNote(bookingRequest.getNote());
        booking.setNumberOfGuests(bookingRequest.getNumberOfGuests());

        //Autovalues
        booking.setTotalPrice(periodService.getAmountOfDays(period) * rental.getPricePerNight());
        booking.setPaid(false);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        booking.setBookingNumber(generateBookingNumber());

        bookingRepository.save(booking);

        return new PostBookingResponse("Rental has been booked successfully",
                booking.getRental().getTitle(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getNote(),
                booking.getBookingStatus());
    }


    public List<AllBookingsResponse> getAllBookings() {
        //Finds all bookings, converts to DTO and returns list.
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::convertToAllBookingsResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        return convertToBookingResponse(booking);
    }

    private BookingResponse convertToBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getBookingNumber(),
                booking.getRental().getTitle(),
                booking.getUser().getUsername(),
                booking.getNumberOfGuests(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getBookingStatus(),
                booking.getNote(),
                booking.getCreatedAt()
        );
    }

    private AllBookingsResponse convertToAllBookingsResponse(Booking booking) {
        return new AllBookingsResponse(
                booking.getRental().getTitle(),
                booking.getUser().getUsername(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getBookingStatus()
        );
    }



    //https://www.baeldung.com/java-uuid-unique-long-generation
    //https://www.baeldung.com/java-secure-random
    //Can have more validation later, for ex. checking uniqueness in combination with rental/user.
    private String generateBookingNumber() {
        SecureRandom secureRandom = new SecureRandom();
        Integer randomPositiveLong = Math.abs(secureRandom.nextInt());
        return randomPositiveLong.toString();
    }
}
