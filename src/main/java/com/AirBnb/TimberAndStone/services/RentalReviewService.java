package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.dto.RentalReviewResponse;
import com.AirBnb.TimberAndStone.exceptions.ConflictException;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.Booking;
import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.RentalReview;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.RentalReviewRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.requests.rentalReview.RentalReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class RentalReviewService {
    private final RentalReviewRepository rentalReviewRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    public RentalReviewService(RentalReviewRepository rentalReviewRepository, RentalRepository rentalRepository, UserRepository userRepository, UserService userService, BookingRepository bookingRepository) {
        this.rentalReviewRepository = rentalReviewRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
    }
    public RentalReviewResponse createRentalReview(RentalReviewRequest rentalReviewRequest) {

        RentalReview rentalReview = new RentalReview();

        rentalReview.setFromUser(rentalReviewRequest.getFromUser());
        rentalReview.setToRental(rentalReviewRequest.getToRental());
        rentalReview.setRating(rentalReviewRequest.getRating());
        rentalReview.setReview(rentalReviewRequest.getReview());
        rentalReview.setCreatedAt(LocalDate.now());
        rentalReview.setUpdatedAt(LocalDate.now());

        rentalReviewRepository.save(rentalReview);
        return new RentalReviewResponse("New rental review created", rentalReview.getToRental(), rentalReview.getRating());
    }
    public List<RentalReview> getAllRentalReviews () {
        return rentalReviewRepository.findAll();
    }
    public RentalReview getRentalReviewById(String id) {
        return rentalReviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Rental review not found"));

    }
    public RentalReview updateRentalReviewById(String id, RentalReview rentalReview) {
        RentalReview existingRentalReview = rentalReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental review not found"));

        if (rentalReview.getReview() != null) {
            existingRentalReview.setReview(rentalReview.getReview());
        }

        return rentalReviewRepository.save(existingRentalReview);
    }





    //------------------------------------------- HELPERS --------------------------------------------------------------


    private void validateRentalReviewRequest(RentalReviewRequest rentalReviewRequest) {

        Booking booking = bookingRepository.findByBookingNumber(rentalReviewRequest.getBookingNumber());

        if (booking == null) {
            throw new ResourceNotFoundException("Booking not found");
        }

        if(rentalReviewRequest.getRating() < 1 || rentalReviewRequest.getRating() > 5) {
            throw new IllegalArgumentException("Rating has to be between 1 and 5");
        }

        if(rentalReviewRequest.getRating() == null) {
            throw new IllegalArgumentException("Please enter a rating.");
        }

        if(rentalReviewRequest.getReview() == null || rentalReviewRequest.getReview().isEmpty()) {
            throw new IllegalArgumentException("Please enter a review.");
        }
        //Check so loggedin user is the user
        if(!userService.getAuthenticated().getId().equals(booking.getUser().getId())) {
            throw new UnauthorizedException("You are not the user of this booking!");
        }
        //Check so the booking is confirmed (and therefore, also paid)
        if(!booking.getBookingStatus().equals(BookingStatus.CONFIRMED)) {
            throw new IllegalArgumentException("Booking status must be confirmed before it can be reviewed.");
        }
        //Check so the bookings end date has passed.
        if(booking.getPeriod().getEndDate().getDayOfYear() > LocalDateTime.now().getDayOfYear()) {
            throw new ConflictException("The bookings enddate must expire before posting a review.");
        }
        //Check so the booking hasn´t been reviewed - NOT YET POSSIBLE
        /*
        if(booking.getUserReviewed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Booking has to be reviewed.");
        }
         */




    }


    }





