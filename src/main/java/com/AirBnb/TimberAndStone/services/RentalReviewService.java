package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.dto.RentalReviewResponse;
import com.AirBnb.TimberAndStone.exceptions.ConflictException;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.Booking;
import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.models.RentalReview;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.RentalReviewRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.requests.rentalReview.RentalReviewRequest;
import org.springframework.stereotype.Service;

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
        // validate rentalReviewRequest
        validateRentalReviewRequest(rentalReviewRequest);

        RentalReview rentalReview = new RentalReview();
        Booking booking = bookingRepository.findByBookingNumber(rentalReviewRequest.getBookingNumber());

        Rental rental = booking.getRental();

        // add and update to the rentals rating
        updateRentalRating(rentalReviewRequest, rental);

        //Set user to authenticated
        rentalReview.setFromUser(userService.getAuthenticated());
        //Set rental to bookings rental
        rentalReview.setToRental(booking.getRental());

        rentalReview.setRating(rentalReviewRequest.getRating());
        rentalReview.setReview(rentalReviewRequest.getReview());

        rentalReviewRepository.save(rentalReview);

        return convertToRentalReviewResponse(rentalReview, booking.getRental(), "The rental has been reviewed successfully");

    }
    public List<RentalReview> getAllRentalReviews () {
        return rentalReviewRepository.findAll();
    }

    public RentalReview getRentalReviewById(String id) {
        return rentalReviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Rental review not found"));

    }

    public RentalReviewResponse updateRentalReviewById(String id, RentalReviewRequest rentalReviewRequest) {
        // check to see if the review exists
        RentalReview existingRentalReview = rentalReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental review not found"));
        // validate rentalReviewRequest
        validateRentalReviewRequest(rentalReviewRequest);

        RentalReview rentalReview = new RentalReview();
        Booking booking = bookingRepository.findByBookingNumber(rentalReviewRequest.getBookingNumber());
        Rental rental = booking.getRental();
        updateUpdateRentalRating(rentalReviewRequest, rental, rentalReview, id);

        if (rentalReviewRequest.getRating() != null) {
          existingRentalReview.setRating(rentalReviewRequest.getRating());
        }
        if (rentalReviewRequest.getReview() != null) {
            existingRentalReview.setReview(rentalReviewRequest.getReview());
        }

        rentalReviewRepository.save(existingRentalReview);
        return convertToRentalReviewResponse(existingRentalReview, booking.getRental(), "The rentalReview has been updated successfully");
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

    private RentalReviewResponse convertToRentalReviewResponse(RentalReview rentalReview, Rental rental, String message) {
        RentalReviewResponse response = new RentalReviewResponse();
        response.setMessage(message);
        response.setUser(rentalReview.getFromUser().getUsername());
        response.setRental(rental.getTitle());
        response.setRating(rentalReview.getRating());
        response.setReview(rentalReview.getReview());
        return response;
    }


    private void updateRentalRating(RentalReviewRequest rentalReviewRequest, Rental rental) {

        Integer numberOfRatings = rental.getRating().getNumberOfRatings();
        Double averageRating = rental.getRating().getAverageRating();
        Integer rating = rentalReviewRequest.getRating();

        averageRating = averageRating * numberOfRatings + rating;
        numberOfRatings = numberOfRatings + 1;
        averageRating = averageRating / numberOfRatings;

        rental.getRating().setNumberOfRatings(numberOfRatings);
        rental.getRating().setAverageRating(averageRating);
        rental.setRating(rental.getRating());

        rentalRepository.save(rental);

    }

    private void updateUpdateRentalRating(RentalReviewRequest rentalReviewRequest, Rental rental, RentalReview rentalReview, String id) {

        rentalReview = getRentalReviewById(id);

        Integer numberOfRatings = rental.getRating().getNumberOfRatings();
        Double averageRating = rental.getRating().getAverageRating();
        Integer rating = rentalReviewRequest.getRating();


        averageRating = averageRating * numberOfRatings - rentalReview.getRating() + rating;
        numberOfRatings = numberOfRatings;
        averageRating = averageRating / numberOfRatings;

        rental.getRating().setNumberOfRatings(numberOfRatings);
        rental.getRating().setAverageRating(averageRating);
        rental.setRating(rental.getRating());

        rentalRepository.save(rental);

    }

}





