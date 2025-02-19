package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dto.RentalReviewDTO;
import com.AirBnb.TimberAndStone.dto.RentalReviewResponse;
import com.AirBnb.TimberAndStone.models.RentalReview;
import com.AirBnb.TimberAndStone.services.RentalReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentalreview")
public class RentalReviewController {

    private final RentalReviewService rentalReviewService;

    public RentalReviewController(RentalReviewService rentalReviewService) {
        this.rentalReviewService = rentalReviewService;
    }

    @PostMapping
    public ResponseEntity<?> createRentalReview(@Valid @RequestBody RentalReviewDTO rentalReviewDTO) {
        RentalReviewResponse rentalReviewResponse = rentalReviewService.createRentalReview(rentalReviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalReviewResponse);
    }
    @GetMapping
    public ResponseEntity<?> getAllRentalReviews() {
        List<RentalReview> rentalReviews = rentalReviewService.getAllRentalReviews();
        return ResponseEntity.ok(rentalReviews);
    }

}
