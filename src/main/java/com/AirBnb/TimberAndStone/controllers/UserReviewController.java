package com.AirBnb.TimberAndStone.controllers;


import com.AirBnb.TimberAndStone.models.UserReview;
import com.AirBnb.TimberAndStone.services.UserReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userreviews")
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



}



