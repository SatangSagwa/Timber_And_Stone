package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.exceptions.ConflictException;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.Booking;
import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.models.UserReview;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.repositories.UserReviewRepository;
import com.AirBnb.TimberAndStone.requests.userReview.UserReviewRequest;
import com.AirBnb.TimberAndStone.responses.GetUserReviewResponse;
import com.AirBnb.TimberAndStone.responses.UserReviewResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserReviewService {
    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;


    public UserReviewService(UserReviewRepository userReviewRepository, UserRepository userRepository, UserService userService, BookingService bookingService, BookingRepository bookingRepository) {
        this.userReviewRepository = userReviewRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }
    public UserReviewResponse createUserReview(UserReviewRequest request) {
        validateUserReviewRequest(request);

        UserReview userReview = new UserReview();
        Booking booking = bookingRepository.findByBookingNumber(request.getBookingNumber());

        //Set host to authenticated
        userReview.setFromHost(userService.getAuthenticated());
        //Set user to bookings user
        userReview.setToUser(booking.getUser());
        userReview.setRating(request.getRating());
        userReview.setReview(request.getReview());

        userReviewRepository.save(userReview);
        UserReviewResponse response = convertToUserReviewResponse(userReview, booking.getRental(), "User has been reviewed successfully");

        return response;
    }

    public List<GetUserReviewResponse> getAllUserReviews() {
        List<UserReview> userReviews = userReviewRepository.findAll();
        return userReviews.stream()
                .map(this::convertToGetUserReviewResponse)
                .collect(Collectors.toList());

    }

    public UserReview getUserReviewById(String id) {
        UserReview userReview = userReviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Userreview not found"));
        return userReview;
    }

    private void validateUserReviewRequest(UserReviewRequest request) {
        Booking booking = bookingRepository.findByBookingNumber(request.getBookingNumber());

        if (booking == null) {
            throw new ResourceNotFoundException("Booking not found");
        }

        if(request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating has to be between 1 and 5");
        }

        if(request.getRating() == null) {
            throw new IllegalArgumentException("Please enter a rating.");
        }

        if(request.getReview() == null || request.getReview().isEmpty()) {
            throw new IllegalArgumentException("Please enter a review.");
        }
        //Check so logged in user is the host
        if(!userService.getAuthenticated().getId().equals(booking.getRental().getHost().getId())) {
            throw new UnauthorizedException("You are not the host of this booking!");
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
        if(booking.getReviewed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Booking has to be reviewed.");
        }
         */

    }

    private UserReviewResponse convertToUserReviewResponse(UserReview userReview, Rental rental, String message) {
        UserReviewResponse userReviewResponse = new UserReviewResponse();
        userReviewResponse.setMessage(message);
        userReviewResponse.setUser(userReview.getToUser().getUsername());
        userReviewResponse.setHost(userReview.getFromHost().getUsername());
        userReviewResponse.setRental(rental.getTitle());
        userReviewResponse.setRating(userReview.getRating());
        userReviewResponse.setReview(userReview.getReview());
        return userReviewResponse;
    }
}
