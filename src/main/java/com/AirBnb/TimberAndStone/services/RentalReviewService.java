package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.dto.RentalReviewDTO;
import com.AirBnb.TimberAndStone.dto.RentalReviewResponse;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.models.RentalReview;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.RentalReviewRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class RentalReviewService {
    private final RentalReviewRepository rentalReviewRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public RentalReviewService(RentalReviewRepository rentalReviewRepository, RentalRepository rentalRepository, UserRepository userRepository, UserService userService) {
        this.rentalReviewRepository = rentalReviewRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }
    public RentalReviewResponse createRentalReview(RentalReviewDTO rentalReviewDTO) {

        RentalReview rentalReview = new RentalReview();

        rentalReview.setFromUser(rentalReviewDTO.getFromUser());
        rentalReview.setToRental(rentalReviewDTO.getToRental());
        rentalReview.setRating(rentalReviewDTO.getRating());
        rentalReview.setReview(rentalReviewDTO.getReview());
        rentalReview.setCreatedAt(LocalDate.now());
        rentalReview.setUpdatedAt(LocalDate.now());

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

    }





