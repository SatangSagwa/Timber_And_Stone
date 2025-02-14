package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RentalService {

   private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }


    public Rental createRental(Rental rental) {
        // add advanced validation
        return rentalRepository.save(rental);
    }



    public List<Rental> getAllRentals () {
        return rentalRepository.findAll();
    }


    public Rental getRentalById(String id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found"));
        return rental;

    }

    public List<Rental> getRentalsByCategory(Category category) {
    return rentalRepository.findByCategory(category);
    }

    // kolla igenom vad som faktiskt bör ligga i patch och se hur det fungarar med @annotation createdat and updatedAt
    public Rental patchRentalById(String id, Rental rental) {
        Rental existingRental = rentalRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found"));

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found"));
        rentalRepository.delete(rental);
    }





}
