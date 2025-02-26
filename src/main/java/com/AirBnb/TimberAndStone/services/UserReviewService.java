package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.models.Booking;
import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.User;
import com.AirBnb.TimberAndStone.models.UserReview;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.repositories.UserReviewRepository;
import com.AirBnb.TimberAndStone.requests.UserReviewRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

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
    public UserReview createUserReview(UserReviewRequest request) {
        UserReview userReview = new UserReview();
        Booking booking = bookingRepository.findByBookingNumber(request.getBookingNumber());

        if (booking == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        }

        userReview.setFromHost(userService.getAuthenticated());
        userReview.setToUser(booking.getUser());
        userReview.setRating(request.getRating());
        userReview.setReview(request.getReview());

        //userReview.setCreatedAt(LocalDate.now());
        //userReview.setUpdatedAt(LocalDate.now());

        validateUserReview(userReview, booking);

        return userReviewRepository.save(userReview);
    }

    public List<UserReview> getAllUserReviews() {
        return userReviewRepository.findAll();

    }

    public UserReview getUserReviewById(String id) {
        UserReview userReview = userReviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Userreview not found"));
        return userReview;
    }

    public List<UserReview> getUserReviewBytoUser(User toUser) {
        List<UserReview> userReview = userReviewRepository.getUserReviewBytoUser(toUser);
        return userReviewRepository.getUserReviewBytoUser(toUser);
    }

    private void validateUserReview(UserReview userReview, Booking booking) {
        if(userReview.getFromHost() == null || userReview.getFromHost().equals("")) {
            throw new IllegalArgumentException("fromHost cannot be null or empty");
        }
        if(userReview.getToUser() == null || userReview.getToUser().equals("")) {
            throw new IllegalArgumentException("toUser cannot be null or empty");
        }
        if(userReview.getRating() < 1 || userReview.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        userRepository.findById(userReview.getToUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.findById(userReview.getFromHost().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Host not found"));

        //Check so host owns this rental.
        if(!userReview.getFromHost().getId().equals(booking.getRental().getHost().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the host of this booking");
        }
        //Check so the booking is paid
        if(!booking.getBookingStatus().equals(BookingStatus.CONFIRMED)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Booking has to be confirmed to make a review.");
        }

        if(booking.getPeriod().getEndDate().getDayOfYear() > LocalDateTime.now().getDayOfYear()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Booking has to be past end date.");
        }

    }
}
