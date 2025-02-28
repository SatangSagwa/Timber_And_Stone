package com.AirBnb.TimberAndStone.controllers;


import com.AirBnb.TimberAndStone.requests.userReview.UserReviewRequest;
import com.AirBnb.TimberAndStone.responses.GetUserReviewResponse;
import com.AirBnb.TimberAndStone.responses.UserReviewResponse;
import com.AirBnb.TimberAndStone.services.UserReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserReviewResponse> createUserReview(@Valid @RequestBody UserReviewRequest request) {
        UserReviewResponse response = userReviewService.createUserReview(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    // Get all userReviews
    @GetMapping
    public ResponseEntity<List<GetUserReviewResponse>> getAllUserReviews() {
        List<GetUserReviewResponse> userReviews = userReviewService.getAllUserReviews();
        return new ResponseEntity<>(userReviews, HttpStatus.OK);
    }
    // Get userReview by id
    @GetMapping("/{id}")
    public ResponseEntity<GetUserReviewResponse> getUserReviewById(@Valid @PathVariable String id) {
        GetUserReviewResponse userReview = userReviewService.getUserReviewById(id);
        return new ResponseEntity<>(userReview, HttpStatus.OK);
    }

    /*@PatchMapping("/{id}")
    public ResponseEntity<UserReviewResponse> updateUserReviewById(@Valid @PathVariable String id, @RequestBody UserReviewRequest userReviewRequest) {
            return new ResponseEntity<>(userReviewService.updateUserReviewById(id, userReviewRequest), HttpStatus.OK);


    }*/

}



