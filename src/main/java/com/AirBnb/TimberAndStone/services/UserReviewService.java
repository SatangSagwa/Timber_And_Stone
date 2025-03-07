package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.exceptions.ConflictException;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.*;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.repositories.UserReviewRepository;
import com.AirBnb.TimberAndStone.requests.userReview.PatchUserReviewRequest;
import com.AirBnb.TimberAndStone.requests.userReview.UserReviewRequest;
import com.AirBnb.TimberAndStone.responses.userReview.GetUserReviewResponse;
import com.AirBnb.TimberAndStone.responses.userReview.UserReviewResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserReviewService {
    private final UserReviewRepository userReviewRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    private final String noReviewsYet = "There are no reviews yet!";
    private final UserRepository userRepository;


    public UserReviewService(UserReviewRepository userReviewRepository, UserService userService, BookingRepository bookingRepository, UserRepository userRepository) {
        this.userReviewRepository = userReviewRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }
    public UserReviewResponse createUserReview(UserReviewRequest request) {
        validateUserReviewRequest(request);


        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if(booking.getReviewedByHost()) {
            throw new IllegalArgumentException("You have already left a review for this visit!");
        }

        UserReview userReview = new UserReview();
        User user = booking.getUser();

        //Set host to authenticated
        userReview.setFromHost(userService.getAuthenticated());
        //Set user to bookings user
        userReview.setToUser(booking.getUser());
        userReview.setRating(request.getRating());
        userReview.setReview(request.getReview());

        //Set booking to found booking.
        userReview.setBooking(booking);

        // add and update to the user rating
        ratingService.updateUserRating(request, user);

        booking.setReviewedByHost(true);
        bookingRepository.save(booking);
        userReviewRepository.save(userReview);

        return convertToUserReviewResponse(userReview, booking.getRental(), "User has been reviewed successfully");
    }

    public UserReviewResponse updateUserReviewById(String id, PatchUserReviewRequest request) {
        // Check if the review exists
        UserReview existingUserReview = userReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User review not found"));

        // Validate the rating and review inputs before proceeding
        validateUserReviewRequest(request, existingUserReview);

        Booking booking = bookingRepository.findById(existingUserReview.getBooking().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // Use the booking.getUser, request and existingRentalReview to update rating in rental
        ratingService.updateRentalRating(existingUserReview, request, booking.getUser());

        if (request.getRating() != null) {
            existingUserReview.setRating(request.getRating());
        }
        if (request.getReview() != null) {
            existingUserReview.setReview(request.getReview());
        }



        userReviewRepository.save(existingUserReview);

        return convertToUserReviewResponse(existingUserReview, booking.getRental(), "The review has been updated successfully");
    }




    public List<GetUserReviewResponse> getAllUserReviews() {
        List<UserReview> userReviews = userReviewRepository.findAll();
        return userReviews.stream()
                .map(this::convertToGetUserReviewResponse)
                .collect(Collectors.toList());

    }


    public GetUserReviewResponse getUserReviewById(String id) {
        UserReview userReview = userReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Userreview not found"));
        return convertToGetUserReviewResponse(userReview);
    }

    public Optional<?> getUserReviewsByBooking(String bookingId, Boolean ascending, Boolean descending, Boolean latest, Boolean oldest) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if(!booking.getRental().getHost().getId().equals(userService.getAuthenticated().getId())) {
            throw new IllegalArgumentException("You are not authorized to view these reviews!");
        }

        List<UserReview> reviews = userReviewRepository.findByToUserId(booking.getUser().getId());

        if(reviews.isEmpty()) {
            return Optional.of(noReviewsYet);
        }

        List<UserReview> sortedReviews = sortReviews(reviews, ascending, descending, latest, oldest);

        return Optional.of(sortedReviews.stream()
                .map(this::convertToGetUserReviewResponse)
                .collect(Collectors.toList()));
    }

    public Optional<?> getMyReviews(Boolean ascending, Boolean descending, Boolean latest, Boolean oldest) {
        List<UserReview> myReviews = userReviewRepository.findByToUserId(userService.getAuthenticated().getId());
        if(myReviews.isEmpty()) {
            return Optional.of(noReviewsYet);
        }

        List<UserReview> sortedReviews = sortReviews(myReviews, ascending, descending, latest, oldest);

        return Optional.of(sortedReviews.stream()
                .map(this::convertToGetUserReviewResponse)
                .collect(Collectors.toList()));
    }




    // ------------------------------ HELPERS --------------------------------------------------------------------------
    private void validateUserReviewRequest(UserReviewRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

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

    private void validateUserReviewRequest(PatchUserReviewRequest request, UserReview review) {

        /*
        RentalReview review = rentalReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found2"));

         */
        if (!userService.getAuthenticated().getId().equals(review.getFromHost().getId())) {
            throw new UnauthorizedException("You are not the owner of this review!");
        }

        if(request.getRating() != null) {
            if(request.getRating() < 1 || request.getRating() > 5) {
                throw new IllegalArgumentException("Rating has to be between 1 and 5");
            }
        }

        //Check so loggedin user is the user
        if(!userService.getAuthenticated().getId().equals(review.getFromHost().getId())) {
            throw new UnauthorizedException("You are not the user of this booking!");
        }
        //Check so the booking is confirmed (and therefore, also paid)
        if(!review.getBooking().getBookingStatus().equals(BookingStatus.CONFIRMED)) {
            throw new IllegalArgumentException("Booking status must be confirmed before it can be reviewed.");
        }
    }

    private List<UserReview> sortReviews(List<UserReview> reviews, Boolean ascending, Boolean descending, Boolean latest, Boolean oldest) {
        //Sort by latest if no option has been chosen
        if(!ascending && !descending && !latest && !oldest) {
            latest = true;
        }

        if(!reviews.isEmpty()) {
            if(ascending) {
                Comparator<UserReview> comparator = Comparator.comparingInt(UserReview::getRating);
                reviews.sort(comparator);

            } else if (descending) {
                Comparator<UserReview> comparator = Comparator.comparingInt(UserReview::getRating).reversed();
                reviews.sort(comparator);

            } else if (latest) {
                Comparator<UserReview> comparator = Comparator.comparing(UserReview::getCreatedAt).reversed();
                reviews.sort(comparator);

            } else if (oldest) {
                Comparator<UserReview> comparator = Comparator.comparing(UserReview::getCreatedAt);
                reviews.sort(comparator);
            }
        }
        return reviews;
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

    private GetUserReviewResponse convertToGetUserReviewResponse(UserReview userReview) {
        GetUserReviewResponse response = new GetUserReviewResponse();
        response.setUser(userReview.getToUser().getUsername());
        response.setHost(userReview.getFromHost().getUsername());
        response.setRating(userReview.getRating());
        response.setReview(userReview.getReview());
        response.setDate(userReview.getCreatedAt());
        return response;
    }

    }

