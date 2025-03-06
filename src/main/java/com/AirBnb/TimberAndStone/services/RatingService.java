package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.models.RentalReview;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.RentalReviewRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.requests.rentalReview.PatchRentalReviewRequest;
import com.AirBnb.TimberAndStone.requests.rentalReview.RentalReviewRequest;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private final RentalReviewRepository rentalReviewRepository;
    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public RatingService(RentalReviewRepository rentalReviewRepository, RentalRepository rentalRepository, UserService userService, BookingRepository bookingRepository, UserRepository userRepository) {
        this.rentalReviewRepository = rentalReviewRepository;
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }





    //For create - adds to numberOfRatings & updates avgRating
    public void updateRentalRating(RentalReviewRequest rentalReviewRequest, Rental rental) {

        // Retrieves the current Rental avg + numbOf Rating
        Integer numberOfRatings = rental.getRating().getNumberOfRatings();
        Double averageRating = rental.getRating().getAverageRating();

        // Retrieves the new Rating that we want to add to the avg and numbOf Rating
        Integer newRating = rentalReviewRequest.getRating();

        // Calculates the new avgRating and adds +1 to numbOfRatings
        averageRating = averageRating * numberOfRatings + newRating;
        numberOfRatings = numberOfRatings + 1;
        averageRating = averageRating / numberOfRatings;

        // Updates avgRating and numbOfRatings in Rental
        rental.getRating().setNumberOfRatings(numberOfRatings);
        rental.getRating().setAverageRating(averageRating);
        rental.setRating(rental.getRating());

        rentalRepository.save(rental);
    }


    //For update - finds by id
    public void updateRentalRating(RentalReview existingRentalReview, PatchRentalReviewRequest request, Rental rental) {

        System.out.println("existingRATING: " + existingRentalReview.getRating());

        // Retrieves the current Rental avg + numbOf Rating
        Integer numberOfRatings = rental.getRating().getNumberOfRatings();
        Double averageRating = rental.getRating().getAverageRating();

        // Retrieves the old rating of the rentalReview you want to update
        Integer ratingToBeUpdated = existingRentalReview.getRating();

        // Retrieves the new Rating that we want to add to the avg and numbOf Rating
        Integer newRating = request.getRating();

        System.out.println("newRating = " + request.getRating());
        System.out.println("averageRating = " + averageRating);
        System.out.println("numberOfRatings = " + numberOfRatings);

        // Calculates the new avgRating and adds +1 to numbOfRatings
        averageRating = averageRating * numberOfRatings - existingRentalReview.getRating() + newRating;
        averageRating = averageRating / numberOfRatings;

        // Updates avgRating and numbOfRatings in Rental
        rental.getRating().setNumberOfRatings(numberOfRatings);
        rental.getRating().setAverageRating(averageRating);
        rental.setRating(rental.getRating());
        System.out.println("Rating :" + rental.getRating().getAverageRating() + "numb: " + rental.getRating().getNumberOfRatings());
        rentalRepository.save(rental);

    }



}
