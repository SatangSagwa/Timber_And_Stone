package com.AirBnb.TimberAndStone.controllers;


import com.AirBnb.TimberAndStone.dtos.requests.userReview.PatchUserReviewRequest;
import com.AirBnb.TimberAndStone.dtos.requests.userReview.UserReviewRequest;
import com.AirBnb.TimberAndStone.dtos.responses.userReview.GetUserReviewResponse;
import com.AirBnb.TimberAndStone.dtos.responses.userReview.UserReviewResponse;
import com.AirBnb.TimberAndStone.services.UserReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/me/{ascending}/{descending}/{latest}/{oldest}")
    public ResponseEntity<Optional<?>> getMyReviews(@Valid @PathVariable Boolean ascending,
                                                    @Valid @PathVariable Boolean descending,
                                                    @Valid @PathVariable Boolean latest,
                                                    @Valid @PathVariable Boolean oldest) {
        Optional<?> response = userReviewService.getMyReviews(ascending, descending, latest, oldest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{bookingId}/{ascending}/{descending}/{latest}/{oldest}")
    public ResponseEntity<Optional<?>> getUserReviewsByUserId(@Valid @PathVariable String bookingId,
                                                              @Valid @PathVariable Boolean ascending,
                                                              @Valid @PathVariable Boolean descending,
                                                              @Valid @PathVariable Boolean latest,
                                                              @Valid @PathVariable Boolean oldest) {
        Optional<?> response = userReviewService.getUserReviewsByBooking(bookingId, ascending, descending, latest, oldest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserReviewResponse> updateUserReviewById(@Valid @PathVariable String id, @RequestBody PatchUserReviewRequest request) {
        return new ResponseEntity<>(userReviewService.updateUserReviewById(id, request), HttpStatus.OK);
    }
}



