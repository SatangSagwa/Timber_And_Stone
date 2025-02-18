package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.models.UserReview;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.repositories.UserReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class UserReviewService {
    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;


    public UserReviewService(UserReviewRepository userReviewRepository, UserRepository userRepository) {
        this.userReviewRepository = userReviewRepository;
        this.userRepository = userRepository;

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

    /*public List<UserReview> getAllUserReviews() {
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
    public List<UserReview> getUserReviewByFromHost(User fromHost) {
        List<UserReview> userReview = userReviewRepository.getUserReviewByFromHost(fromHost);
        return userReviewRepository.getUserReviewByFromHost(fromHost);
    }*/
}
