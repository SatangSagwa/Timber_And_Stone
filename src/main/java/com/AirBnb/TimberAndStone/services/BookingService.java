package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.dto.BookingRequest;
import com.AirBnb.TimberAndStone.dto.BookingResponse;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.*;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public BookingResponse createBooking(BookingRequest bookingRequest) {
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

        //Autovalues
        booking.setTotalPrice(periodService.getAmountOfDays(period) * rental.getPricePerNight());
        booking.setPaid(false);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDate.now());
        booking.setUpdatedAt(LocalDate.now());
        bookingRepository.save(booking);

        return new BookingResponse("Rental has been booked successfully",
                booking.getRental().getTitle(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getNote(),
                booking.getBookingStatus());
    }
}
