package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.requests.rentalReview.RentalReviewRequest;
import com.AirBnb.TimberAndStone.dto.RentalReviewResponse;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.models.RentalReview;
import com.AirBnb.TimberAndStone.repositories.RentalReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class RentalReviewService {
    private final RentalReviewRepository rentalReviewRepository;

    public RentalReviewService(RentalReviewRepository rentalReviewRepository) {
        this.rentalReviewRepository = rentalReviewRepository;
    }
    public RentalReviewResponse createRentalReview(RentalReviewRequest rentalReviewRequest) {

        RentalReview rentalReview = new RentalReview();

        rentalReview.setFromUser(rentalReviewRequest.getFromUser());
        rentalReview.setToRental(rentalReviewRequest.getToRental());
        rentalReview.setRating(rentalReviewRequest.getRating());
        rentalReview.setReview(rentalReviewRequest.getReview());

        rentalReviewRepository.save(rentalReview);
        return new RentalReviewResponse("New rental review created", rentalReview.getToRental(), rentalReview.getRating());
    }
    public List<RentalReview> getAllRentalReviews () {
        return rentalReviewRepository.findAll();
    }

    public RentalReview getRentalReviewById(String id) {
        return rentalReviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Rental review not found"));

    }

    public RentalReview updateRentalReviewById(String id, RentalReview rentalReview) {
        RentalReview existingRentalReview = rentalReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental review not found"));

        if (rentalReview.getReview() != null) {
            existingRentalReview.setReview(rentalReview.getReview());
        }

        return rentalReviewRepository.save(existingRentalReview);
    }










    // ----------------------------------------- HELPERS ---------------------------------------------------------------


    private void validateRentalReviewRequest(RentalReviewRequest rentalReviewRequest) {

    }



    }





