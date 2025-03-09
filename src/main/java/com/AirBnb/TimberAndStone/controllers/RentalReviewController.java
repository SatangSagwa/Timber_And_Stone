package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dtos.requests.rentalReview.PatchRentalReviewRequest;
import com.AirBnb.TimberAndStone.dtos.requests.rentalReview.RentalReviewRequest;
import com.AirBnb.TimberAndStone.dtos.responses.rentalReview.RentalReviewResponse;
import com.AirBnb.TimberAndStone.dtos.responses.rentalReview.RentalReviewsResponse;
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
        List<RentalReviewsResponse> response = rentalReviewService.getAllRentalReviews();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalReviewById(@PathVariable String id) {
        RentalReviewResponse response = rentalReviewService.getRentalReviewById(id);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<RentalReviewResponse> updateRentalReviewById(@Valid @PathVariable String id, @RequestBody PatchRentalReviewRequest request) {
        return ResponseEntity.ok(rentalReviewService.updateRentalReviewById(id, request));
        //return new ResponseEntity<>(rentalReviewService.updateRentalReviewById(id, rentalReviewRequest), HttpStatus.OK);
        }
        @GetMapping("rental/{id}")
    public ResponseEntity<List<RentalReviewsResponse>> getRentalReviewsByRentalId(@Valid @PathVariable String id) {
        List<RentalReviewsResponse> rentalReviews = rentalReviewService.getRentalReviewByRentalId(id);
        return ResponseEntity.ok(rentalReviews);

        }

        @GetMapping("rental/host/{id}")
        public ResponseEntity<List<RentalReviewsResponse>> getRentalReviewsByHostId(@Valid @PathVariable String id) {
         List<RentalReviewsResponse> rentalReviews = rentalReviewService.getRentalReviewByHostId(id);
            return ResponseEntity.ok(rentalReviews);
        }

    }


