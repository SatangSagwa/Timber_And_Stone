package com.AirBnb.TimberAndStone.services;


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
import com.AirBnb.TimberAndStone.requests.rentalReview.PatchRentalReviewRequest;
import com.AirBnb.TimberAndStone.requests.rentalReview.RentalReviewRequest;
import com.AirBnb.TimberAndStone.responses.rentalReview.RentalReviewResponse;
import com.AirBnb.TimberAndStone.responses.rentalReview.RentalReviewsResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalReviewService {
    private final RentalReviewRepository rentalReviewRepository;
    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    public RentalReviewService(RentalReviewRepository rentalReviewRepository, RentalRepository rentalRepository, UserService userService, BookingRepository bookingRepository) {
        this.rentalReviewRepository = rentalReviewRepository;
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
    }

    public RentalReviewResponse createRentalReview(RentalReviewRequest request) {

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // validate rentalReviewRequest
        validateRentalReviewRequest(request);

        RentalReview rentalReview = new RentalReview();
        Rental rental = booking.getRental();

        // add and update to the rentals rating
        updateRentalRating(request, rental);

        //Set user to authenticated
        rentalReview.setFromUser(userService.getAuthenticated());
        //Set rental to bookings rental
        rentalReview.setToRental(booking.getRental());
        //Set booking to found booking.
        rentalReview.setBooking(booking);

        rentalReview.setRating(request.getRating());
        rentalReview.setReview(request.getReview());

        rentalReviewRepository.save(rentalReview);

        return convertToRentalReviewResponse(rentalReview, "The rental has been reviewed successfully");

    }

    public List<RentalReviewsResponse> getAllRentalReviews () {
        return rentalReviewRepository.findAll().stream()
                .map(this :: convertToRentalReviewsResponse)
                .collect(Collectors.toList());
    }

    public RentalReviewResponse getRentalReviewById(String id) {
        RentalReview rentalReview = rentalReviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Rental review not found"));

        return convertToRentalReviewResponse(rentalReview, "Review: ");
    }

    /*
    public RentalReviewResponse updateRentalReviewById(String id, PatchRentalReviewRequest request) {
        // check to see if the review exists
        RentalReview existingRentalReview = rentalReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental review not found"));
        // validate rentalReviewRequest
        validateRentalReviewRequest(new RentalReviewRequest(existingRentalReview.getBooking().getId(), request.getRating(), request.getReview()));

        Booking booking = bookingRepository.findByBookingNumberAndUserAndRental(existingRentalReview.getBooking().getBookingNumber(), existingRentalReview.getFromUser(), existingRentalReview.getToRental())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        Rental rental = booking.getRental();
        updateRentalRating(
                new RentalReviewRequest(existingRentalReview.getBooking().getId(), existingRentalReview.getRating(), existingRentalReview.getReview()),
                rental,
                id);

        if (request.getRating() != null) {
          existingRentalReview.setRating(request.getRating());
        }
        if (request.getReview() != null) {
            existingRentalReview.setReview(request.getReview());
        }
updateRentalRating
        rentalReviewRepository.save(existingRentalReview);
        return convertToRentalReviewResponse(existingRentalReview, "The review has been updated successfully");
    }
     */

    public RentalReviewResponse updateRentalReviewById(String id, PatchRentalReviewRequest request) {
        // Check if the review exists
        RentalReview existingRentalReview = rentalReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental review not found"));

        // Validate the rating and review inputs before proceeding
        validateRentalReviewRequest(request, existingRentalReview);

        // find booking
        Booking booking = bookingRepository.findByBookingNumberAndUserAndRental(
                existingRentalReview.getBooking().getBookingNumber(),
                existingRentalReview.getFromUser(),
                existingRentalReview.getToRental()
        ).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        updateRentalRating(existingRentalReview, booking.getRental(), id);

        if (request.getRating() != null) {
            existingRentalReview.setRating(request.getRating());
        }
        if (request.getReview() != null) {
            existingRentalReview.setReview(request.getReview());
        }

        rentalReviewRepository.save(existingRentalReview);

        return convertToRentalReviewResponse(existingRentalReview, "The review has been updated successfully");
    }






    //------------------------------------------- HELPERS --------------------------------------------------------------


    private void validateRentalReviewRequest(RentalReviewRequest rentalReviewRequest) {

        Booking booking = bookingRepository.findById(rentalReviewRequest.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found2"));

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

    private void validateRentalReviewRequest(PatchRentalReviewRequest rentalReviewRequest, RentalReview review) {

        /*
        RentalReview review = rentalReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found2"));

         */
        if(rentalReviewRequest.getRating() != null) {
            if(rentalReviewRequest.getRating() < 1 || rentalReviewRequest.getRating() > 5) {
                throw new IllegalArgumentException("Rating has to be between 1 and 5");
            }
        }

        //Check so loggedin user is the user
        if(!userService.getAuthenticated().getId().equals(review.getFromUser().getId())) {
            throw new UnauthorizedException("You are not the user of this booking!");
        }
        //Check so the booking is confirmed (and therefore, also paid)
        if(!review.getBooking().getBookingStatus().equals(BookingStatus.CONFIRMED)) {
            throw new IllegalArgumentException("Booking status must be confirmed before it can be reviewed.");
        }
    }

    private RentalReviewResponse convertToRentalReviewResponse(RentalReview rentalReview, String message) {
        RentalReviewResponse response = new RentalReviewResponse();
        response.setMessage(message);
        response.setUser(rentalReview.getFromUser().getUsername());
        response.setRental(rentalReview.getToRental().getTitle());
        response.setRating(rentalReview.getRating());
        response.setReview(rentalReview.getReview());
        return response;
    }

    private RentalReviewsResponse convertToRentalReviewsResponse(RentalReview rentalReview) {
        RentalReviewsResponse response = new RentalReviewsResponse();
        response.setRental(rentalReview.getToRental().getTitle());
        response.setReviewer(rentalReview.getFromUser().getUsername());
        response.setRating(rentalReview.getRating());
        response.setReview(rentalReview.getReview());
        return response;
    }


    //For create - adds to numberOfRatings
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

    //For update - finds by id
    private void updateRentalRating(RentalReview rentalReview, Rental rental, String id) {
        RentalReview existingRentalReview = rentalReviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Rental review not found"));

        Integer numberOfRatings = rental.getRating().getNumberOfRatings();
        Double averageRating = rental.getRating().getAverageRating();
        Integer rating = rentalReview.getRating();


        averageRating = averageRating * numberOfRatings - existingRentalReview.getRating() + rating;
        averageRating = averageRating / numberOfRatings;

        rental.getRating().setNumberOfRatings(numberOfRatings);
        rental.getRating().setAverageRating(averageRating);
        rental.setRating(rental.getRating());

        rentalRepository.save(rental);

    }

}





