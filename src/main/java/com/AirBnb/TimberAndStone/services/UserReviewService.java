package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.models.UserReview;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.repositories.UserReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class UserReviewService {
    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;
    // does not exist yet
    //private final RentalRepository rentalRepository;
    public UserReviewService(UserReviewRepository userReviewRepository, UserRepository userRepository) {
        this.userReviewRepository = userReviewRepository;
        this.userRepository = userRepository;
        //this.rentalRepository = rentalRepository;
    }
    public UserReview createUserReview(UserReview userReview) {
        // check for fromHost, toUser and rating
        if(userReview.getFromHost() == null || userReview.getFromHost().equals("")) {
            throw new IllegalArgumentException("fromHost cannot be null or empty");
        }
        if(userReview.getToUser() == null || userReview.getToUser().equals("")) {
            throw new IllegalArgumentException("toUser cannot be null or empty");
        }
        if(userReview.getRating() < 1 || userReview.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        return userReviewRepository.save(userReview);
    }

}
