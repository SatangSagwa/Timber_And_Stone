package com.AirBnb.TimberAndStone.controllers;


import com.AirBnb.TimberAndStone.models.UserReview;
import com.AirBnb.TimberAndStone.services.UserReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/userreviews")
public class UserReviewController {

    private final UserReviewService userReviewService;
    public UserReviewController(UserReviewService userReviewService) {
        this.userReviewService = userReviewService;
    }
    // create userReview
    @PostMapping
    public ResponseEntity<UserReview> createUserReview(@Valid @RequestBody UserReview userReview) {
        UserReview createdUserReview = userReviewService.createUserReview(userReview);
        return new ResponseEntity<>(createdUserReview, HttpStatus.CREATED);
    }
    // Get all userReviews
    @GetMapping
    public ResponseEntity<List<UserReview>> getAllUserReviews() {
        List<UserReview> userReviews = userReviewService.getAllUserReviews();
        return new ResponseEntity<>(userReviews, HttpStatus.OK);
    }
    // Get userReview by id
    @GetMapping("/{id}")
    public ResponseEntity<UserReview> getUserReviewById(@Valid @PathVariable String id) {
        UserReview userReview = userReviewService.getUserReviewById(id);
        return new ResponseEntity<>(userReview, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserReview> updateUserReview(@Valid @PathVariable String id, @RequestBody UserReview userReview) {
       UserReview existingUserReview = userReviewService.getUserReviewById(id);
       existingUserReview.setToUser(userReview.getToUser());
       existingUserReview.setFromHost(userReview.getFromHost());
       existingUserReview.setRating(userReview.getRating());
       existingUserReview.setUpdatedAt(LocalDate.now());
       return new ResponseEntity<>(existingUserReview, HttpStatus.OK);

    }

}



