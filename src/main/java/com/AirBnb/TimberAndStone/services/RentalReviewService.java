package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.dto.RentalResponse;
import com.AirBnb.TimberAndStone.dto.RentalReviewDTO;
import com.AirBnb.TimberAndStone.dto.RentalReviewResponse;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.models.RentalReview;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.RentalReviewRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;

import java.time.LocalDate;

public class RentalReviewService {
    private final RentalReviewRepository rentalReviewRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public RentalReviewService(RentalReviewRepository rentalReviewRepository, RentalRepository rentalRepository, UserRepository userRepository) {
        this.rentalReviewRepository = rentalReviewRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
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
    }

}
