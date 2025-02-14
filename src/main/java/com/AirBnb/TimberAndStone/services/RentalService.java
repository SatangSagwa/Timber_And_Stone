package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.dto.RentalDTO;
import com.AirBnb.TimberAndStone.dto.RentalResponse;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.models.Rating;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.models.User;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

   private final RentalRepository rentalRepository;
   private final UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }


    public RentalResponse createRental(RentalDTO rentalDTO) {

        // Detta under sätter host som den som är inloggad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                        .orElseThrow(() -> new IllegalArgumentException("Rental not found"));


        Rental rental = new Rental();
        Rating rating = new Rating();

        rating.setAverageRating(0.0);
        rating.setNumberOfRatings(0);

        rental.setRating(rating);
        rental.setHost(user);

        // DTON
        rental.setTitle(rentalDTO.getTitle());
        rental.setPhotos(rentalDTO.getPhotos());
        rental.setPricePerNight(rentalDTO.getPricePerNight());
        rental.setAddress(rentalDTO.getAddress());
        rental.setCategory(rentalDTO.getCategory());
        rental.setAmenities(rentalDTO.getAmenities());
        rental.setCapacity(rentalDTO.getCapacity());
        rental.setAvailablePeriods(rentalDTO.getAvailablePeriods());
        rental.setDescription(rentalDTO.getDescription());
        rental.setPolicy(rentalDTO.getPolicy());


        rentalRepository.save(rental);
        return new RentalResponse("New Rental has been created", rental.getTitle()) ;
    }



    public List<Rental> getAllRentals () {
        return rentalRepository.findAll();
    }


    public Rental getRentalById(String id) {
        return  rentalRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Rental not found"));

    }

    public List<Rental> getRentalsByCategory(Category category) {
    return rentalRepository.findByCategory(category);
    }

    // kolla igenom vad som faktiskt bör ligga i patch och se hur det fungarar med @annotation createdat and updatedAt
    public Rental patchRentalById(String id, Rental rental) {
        Rental existingRental = rentalRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Rental not found"));

        if (rental.getTitle() != null) {
            existingRental.setTitle(rental.getTitle());
        }
        if (rental.getPhotos() != null) {
            existingRental.setPhotos(rental.getPhotos());
        }
        if (rental.getPricePerNight() != null) {
            existingRental.setPricePerNight(rental.getPricePerNight());
        }
        if (rental.getRating() != null) {
            existingRental.setRating(rental.getRating());
        }
        if (rental.getHost() != null) {
            existingRental.setHost(rental.getHost());
        }
        if (rental.getAddress() != null) {
            existingRental.setAddress(rental.getAddress());
        }
        if (rental.getCategory() != null) {
            existingRental.setCategory(rental.getCategory());
        }
        if (rental.getAmenities() != null) {
            existingRental.setAmenities(rental.getAmenities());
        }
        if (rental.getCapacity() != null) {
            existingRental.setCapacity(rental.getCapacity());
        }
        if (rental.getAvailablePeriods() != null) {
            existingRental.setAvailablePeriods(rental.getAvailablePeriods());
        }
        if (rental.getDescription() != null) {
            existingRental.setDescription(rental.getDescription());
        }
        if (rental.getPolicy() != null) {
            existingRental.setPolicy(rental.getPolicy());
        }
        if (rental.getCreatedAt() != null) {
            existingRental.setCreatedAt(rental.getCreatedAt());
        }
        if (rental.getUpdatedAt() != null) {
            existingRental.setUpdatedAt(rental.getUpdatedAt());
        }
        return rentalRepository.save(existingRental);
    }

    public void deleteRental(String id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
        rentalRepository.delete(rental);
    }





}
