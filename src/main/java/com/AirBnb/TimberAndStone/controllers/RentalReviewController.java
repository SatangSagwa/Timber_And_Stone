package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.requests.rentalReview.RentalReviewRequest;
import com.AirBnb.TimberAndStone.responses.rentalReview.GetRentalReviewResponse;
import com.AirBnb.TimberAndStone.responses.rentalReview.RentalReviewResponse;
import com.AirBnb.TimberAndStone.models.RentalReview;
import com.AirBnb.TimberAndStone.services.RentalReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentalreviews")
public class RentalReviewController {

    private final RentalReviewService rentalReviewService;

    public RentalReviewController(RentalReviewService rentalReviewService) {
        this.rentalReviewService = rentalReviewService;
    }

    @PostMapping
    public ResponseEntity<?> createRentalReview(@Valid @RequestBody RentalReviewRequest rentalReviewRequest) {
        RentalReviewResponse rentalReviewResponse = rentalReviewService.createRentalReview(rentalReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalReviewResponse);
    }
    @GetMapping
    public ResponseEntity<?> getAllRentalReviews() {
        List<RentalReview> rentalReviews = rentalReviewService.getAllRentalReviews();
        return ResponseEntity.ok(rentalReviews);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalReviewById(@PathVariable String id) {
        RentalReview rentalReview = rentalReviewService.getRentalReviewById(id);
        return ResponseEntity.ok(rentalReview);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<RentalReviewResponse> updateRentalReviewById(@Valid @PathVariable String id, @RequestBody RentalReviewRequest rentalReviewRequest) {
            return new ResponseEntity<>(rentalReviewService.updateRentalReviewById(id, rentalReviewRequest), HttpStatus.OK);
        }
        @GetMapping("rental/{id}")
    public ResponseEntity<List<GetRentalReviewResponse>> getRentalReviewsByRentalId(@Valid @PathVariable String id) {
        List<GetRentalReviewResponse> rentalReviews = rentalReviewService.getRentalReviewByRentalId(id);
        return ResponseEntity.ok(rentalReviews);
        }
        @GetMapping("rental/host/{id}")
        public ResponseEntity<List<GetRentalReviewResponse>> getRentalReviewsByHostId(@Valid @PathVariable String id) {
         List<GetRentalReviewResponse> rentalReviews = rentalReviewService.getRentalReviewByHostId(id);
            return ResponseEntity.ok(rentalReviews);
        }

    }


