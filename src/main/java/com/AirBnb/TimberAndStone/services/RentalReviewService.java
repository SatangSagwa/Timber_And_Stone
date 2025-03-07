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
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.requests.rentalReview.PatchRentalReviewRequest;
import com.AirBnb.TimberAndStone.requests.rentalReview.RentalReviewRequest;
import com.AirBnb.TimberAndStone.responses.rentalReview.RentalReviewResponse;
import com.AirBnb.TimberAndStone.responses.rentalReview.RentalReviewsResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalReviewService {
    private final RentalReviewRepository rentalReviewRepository;
    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public RentalReviewService(RentalReviewRepository rentalReviewRepository, RentalRepository rentalRepository, UserService userService, BookingRepository bookingRepository, UserRepository userRepository) {
        this.rentalReviewRepository = rentalReviewRepository;
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public RentalReviewResponse createRentalReview(RentalReviewRequest request) {

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if(booking.getReviewedByUser()) {
            throw new IllegalArgumentException("You have already left a review for this visit!");
        }

        // validate rentalReviewRequest
        validateRentalReviewRequest(request);

        RentalReview rentalReview = new RentalReview();
        Rental rental = booking.getRental();

        // add and update to the rentals rating
        //updateRentalRating(request, rental);

        //Set user to authenticated
        rentalReview.setFromUser(userService.getAuthenticated());
        //Set rental to bookings rental
        rentalReview.setToRental(booking.getRental());
        //Set booking to found booking.
        rentalReview.setBooking(booking);

        rentalReview.setRating(request.getRating());
        rentalReview.setReview(request.getReview());

        booking.setReviewedByUser(true);
        bookingRepository.save(booking);
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
        Booking booking = bookingRepository.findById(existingRentalReview.getBooking().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        System.out.println("Rental title: " + booking.getRental().getTitle());

        // Use the booking.getRental, id and existingRentalReview to update rating in rental
        //updateRentalRating(existingRentalReview, booking.getRental(), id);

        if (request.getRating() != null) {
            existingRentalReview.setRating(request.getRating());
        }
        if (request.getReview() != null) {
            existingRentalReview.setReview(request.getReview());
        }

        rentalReviewRepository.save(existingRentalReview);

        return convertToRentalReviewResponse(existingRentalReview, "The review has been updated successfully");
    }


    public List<RentalReviewsResponse> getRentalReviewByRentalId(String id) {
        if (id == null || id.isEmpty() || "null".equals(id)) {
            throw new IllegalArgumentException("Rental id cannot be 'null' or empty");
        }
        if (!rentalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rental not found");
        }

        List<RentalReview> rentalReviews = rentalReviewRepository.findByToRentalId(id);

        return rentalReviews.stream()
                .map(this::convertToRentalReviewsResponse)
                .collect(Collectors.toList());
    }

    public List<RentalReviewsResponse> getRentalReviewByHostId(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Host not found");
        }
        List<Rental> rentals = rentalRepository.findByHostId(id);

        if (rentals.isEmpty()) {
            throw new ResourceNotFoundException("No rentals found for this host");
        }
        // New arraylist for rental reviews connected to host
        List<RentalReview> rentalReviews = new ArrayList<>();
        for (Rental rental : rentals) {
            List<RentalReview> reviewsForRental = rentalReviewRepository.findByToRentalId(rental.getId());
            rentalReviews.addAll(reviewsForRental);
        }
        return rentalReviews.stream()
                .map(this::convertToRentalReviewsResponse)
                .collect(Collectors.toList());
    }


    //------------------------------------------- HELPERS --------------------------------------------------------------


    private void validateRentalReviewRequest(RentalReviewRequest rentalReviewRequest) {

        Booking booking = bookingRepository.findById(rentalReviewRequest.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found2"));

        if(booking.getReviewedByUser()) {
            throw new IllegalArgumentException("You have already reviewed your visit");
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

    private void validateRentalReviewRequest(PatchRentalReviewRequest rentalReviewRequest, RentalReview review) {

        /*
        RentalReview review = rentalReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found2"));

         */
        if (!userService.getAuthenticated().getId().equals(review.getFromUser().getId())) {
            throw new UnauthorizedException("You are not the owner of this review!");
        }

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

    /*
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
        System.out.println("existingRATING: " + existingRentalReview.getRating());
        Integer numberOfRatings = rental.getRating().getNumberOfRatings();
        Double averageRating = rental.getRating().getAverageRating();
        Integer rating = rentalReview.getRating();
        System.out.println("newRating = " + rentalReview.getRating());
        System.out.println("averageRating = " + averageRating);
        System.out.println("numberOfRatings = " + numberOfRatings);

        averageRating = averageRating * numberOfRatings - existingRentalReview.getRating() + rating;
        averageRating = averageRating / numberOfRatings;

        rental.getRating().setNumberOfRatings(numberOfRatings);
        rental.getRating().setAverageRating(averageRating);
        rental.setRating(rental.getRating());
        System.out.println("Rating :" + rental.getRating().getAverageRating() + "numb: " + rental.getRating().getNumberOfRatings());
        rentalRepository.save(rental);

    }
    private GetRentalReviewResponse convertToGetRentalReviewResponse(RentalReview rentalReview) {
        Rental rental = rentalReview.getToRental();
        User host = rental.getHost();

        GetRentalReviewResponse getRentalReviewResponse = new GetRentalReviewResponse();
        getRentalReviewResponse.setUser(rentalReview.getFromUser().getUsername());
        getRentalReviewResponse.setRental(rental.getTitle());
        getRentalReviewResponse.setRating(rentalReview.getRating());
        getRentalReviewResponse.setReview(rentalReview.getReview());
        return getRentalReviewResponse;
    }


     */

}





