package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.exceptions.ConflictException;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.*;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.requests.rental.RentalAmenitiesRequest;
import com.AirBnb.TimberAndStone.requests.rental.RentalRequest;
import com.AirBnb.TimberAndStone.responses.rental.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }


// --------------------------------- Methods ---------------------------------------------------------------------------

    public RentalResponse createRental(RentalRequest rentalRequest) {

        // Detta under sätter host som den som är inloggad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        Rental rental = new Rental();
        Rating rating = new Rating();

        // Fields we set ourself
        rating.setAverageRating(0.0);
        rating.setNumberOfRatings(0);
        rental.setRating(rating);
        rental.setHost(user);

        // DTON
        rental.setAddress(rentalRequest.getAddress());
        rental.setAvailablePeriods(rentalRequest.getAvailablePeriods());
        rental.setAmenities(rentalRequest.getAmenities());
        rental.setTitle(rentalRequest.getTitle());
        rental.setPhotos(rentalRequest.getPhotos());
        rental.setPricePerNight(rentalRequest.getPricePerNight());
        rental.setCategory(rentalRequest.getCategory());
        rental.setCapacity(rentalRequest.getCapacity());
        rental.setDescription(rentalRequest.getDescription());
        rental.setPolicy(getValidatedPolicy(rentalRequest.getPolicy()));


        rentalRepository.save(rental);
        return new RentalResponse("New Rental has been created", rental.getTitle());
    }

    public List<GetRentalsResponse> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();

        return rentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }

    public GetRentalsResponse getRentalById(String id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
        return convertToGetRentalsResponse(rental);
    }

    public List<GetRentalsResponse> getRentalsByCategory(Category category) {
        List<Rental> rentals = rentalRepository.findByCategory(category);

        return rentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }

    public List<GetRentalsResponse> getRentalsByPricePerNightRange(Double minPrice, Double maxPrice) {
        List<Rental> rentals = rentalRepository.findByPricePerNightBetweenInclusive(minPrice, maxPrice);

        return rentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }

    // kolla igenom vad som faktiskt bör ligga i patch och se hur det fungarar med @annotation createdat and updatedAt
    public Rental patchRentalById(String id, Rental rental) {
        Rental existingRental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

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
            existingRental.setPolicy(getValidatedPolicy(rental.getPolicy()));
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

    public List<GetRentalsResponse> getRentalsByMinAvgRatingAndMinNumberOfRating(Double minAvgRating, Integer minNumberOfRatings) {
        List<Rental> rentals = rentalRepository.findByRatingAverageRatingGreaterThanEqualAndRatingNumberOfRatingsGreaterThanEqual(minAvgRating, minNumberOfRatings);

        return rentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }

    public List<GetRentalsResponse> getRentalsByCapacity(Integer capacity) {

        if(capacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }

        List<Rental> rentals = rentalRepository.findAll();

        //Filters matching rentals
        rentals = rentals.stream()
                .filter(rental -> rental.getCapacity() >= (capacity))
                .toList();

        //Converts all rentals to DTO and returns
        return rentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }

    public List<GetRentalsResponse> getRentalsByTitle(String title) {

        //Trim whitespace from title
        String trimmedtitle = StringUtils.trimAllWhitespace(title);

        //Trim all whitespace from rentals titles
        List<Rental> trimmedRentals = rentalRepository.findAll();
        for(Rental rental : trimmedRentals) {
            rental.setTitle(StringUtils.trimAllWhitespace(rental.getTitle()));
        }

        //Filter matching rentals to trimmedRentals
        List<Rental> rentals = rentalRepository.findAll();
        trimmedRentals = trimmedRentals.stream()
                .filter(rental -> rental.getTitle().equalsIgnoreCase(trimmedtitle))
                .toList();

        //New list for holding matching rentals
        List<Rental> matchingRentals = new ArrayList<>();

        //For each rental.id, compare to trimmedRental.id and add to matchingRentals.
        for (Rental rental : rentals) {
            for (Rental trimmedRental : trimmedRentals) {
                if(rental.getId().equals(trimmedRental.getId())) {
                    matchingRentals.add(rental);
                }
            }
        }
        //Converts to DTO and returns
        return matchingRentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }

    // https://chatgpt.com/share/67b4a4fb-a588-800b-9894-16722dd3a37d
    public List<GetRentalsResponse> getRentalsByAvailabilityPeriod(LocalDate startDate, LocalDate endDate) {
        List<Rental> rentals = rentalRepository.findAll();
        if (startDate.isAfter(endDate) || startDate.isEqual(endDate) || endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            throw new ConflictException("startDate and endDate have to be in order (startDate - endDate)");
        }
        List<Rental> matchingRentals = rentals.stream()
                .filter(rental -> rental.getAvailablePeriods().stream()
                        .anyMatch(period -> isPeriodMatching(period, startDate, endDate))
                )
                .collect(Collectors.toList());
        return matchingRentals.stream()
                .map(rental -> {
                    List<Period> matchingPeriods = rental.getAvailablePeriods().stream()
                            .filter(period -> isPeriodMatching(period, startDate, endDate))
                            .collect(Collectors.toList());
                    GetRentalsResponse response = convertToGetRentalsResponse(rental);
                    //response.setPeriods(matchingPeriods);
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<GetRentalsResponse> getRentalsDTOByCountry(String country) {
        List<Rental> rentals = getRentalsByCountry(country);

        //return as DTO
        return rentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }

    public List<GetRentalsResponse> getRentalsDTOByCountryAndCity(String country, String city) {
        List<Rental> rentals = getRentalsByCountryAndCity(country, city);

        //return as DTO
        return rentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }

    public List<GetRentalsResponse> getRentalsByAmenities(RentalAmenitiesRequest rentalAmenitiesRequest) {
        List<Rental> rentals = rentalRepository.findAll();
        List<Rental> matchingRentals = rentals.stream()
                .filter(rental -> rental.getAmenities().stream()
                        .anyMatch(amenities -> isAmenitiesMatching(rental.getAmenities(), rentalAmenitiesRequest.getAmenities()))
                )
                .collect(Collectors.toList());

        return matchingRentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }

    public List<GetRentalsResponse> getRentalsByAverageRating(Double averageRating) {
        List<Rental> rentals = rentalRepository.findByRatingAverageRating(averageRating);

        return rentals.stream()
                .map(this::convertToGetRentalsResponse)
                .collect(Collectors.toList());
    }



    // -------------------------- Help Methods -------------------------------------------------------------------------

    private List<Rental> getRentalsByCountry(String country) {

        //Trim whitespace from country input
        String trimmedCountry = StringUtils.trimAllWhitespace(country);

        //Make a list of rentals with trimmed country names.
        List<Rental> trimmedRentals = rentalRepository.findAll();

        for(Rental rental : trimmedRentals) {
            rental.setAddress(new Address(
                    StringUtils.trimAllWhitespace(rental.getAddress().getCountry()),
                    rental.getAddress().getCity(),
                    rental.getAddress().getPostalCode(),
                    rental.getAddress().getStreetName(),
                    rental.getAddress().getStreetNumber(),
                    rental.getAddress().getLatitude(),
                    rental.getAddress().getLongitude()));
        }

        //Filter only matching trimmed rentals to trimmed country
        trimmedRentals = trimmedRentals.stream()
                .filter(rental -> rental.getAddress().getCountry().equalsIgnoreCase(trimmedCountry))
                .toList();

        //New list for holding matching untrimmed rentals, to output the untrimmed country.
        List<Rental> matchingRentals = new ArrayList<>();

        //For each rental.id, compare to trimmedRental.id and add to matchingRentals.
        List<Rental> rentals = rentalRepository.findAll();
        for (Rental rental : rentals) {
            for (Rental trimmedRental : trimmedRentals) {
                if(rental.getId().equals(trimmedRental.getId())) {
                    matchingRentals.add(rental);
                }
            }
        }
        return matchingRentals;
    }

    private List<Rental> getRentalsByCountryAndCity(String country, String city) {
        //Trim all city names
        String trimmedCity = StringUtils.trimAllWhitespace(city);

        //Make a list of matching country rentals with trimmed city names.
        List<Rental> trimmedRentals = getRentalsByCountry(country);

        for(Rental rental : trimmedRentals) {
            rental.setAddress(new Address(
                    rental.getAddress().getCountry(),
                    StringUtils.trimAllWhitespace(rental.getAddress().getCity()),
                    rental.getAddress().getPostalCode(),
                    rental.getAddress().getStreetName(),
                    rental.getAddress().getStreetNumber(),
                    rental.getAddress().getLatitude(),
                    rental.getAddress().getLongitude()));
        }

        //Filter only matching trimmed rentals to trimmed city
        trimmedRentals = trimmedRentals.stream()
                .filter(rental -> rental.getAddress().getCity().equalsIgnoreCase(trimmedCity))
                .toList();

        //New list for holding matching untrimmed rentals (to output the untrimmed city.)
        List<Rental> matchingRentals = new ArrayList<>();

        //For each rental.id, compare to trimmedRental.id and add to matchingRentals.
        List<Rental> rentals = rentalRepository.findAll();
        for (Rental rental : rentals) {
            for (Rental trimmedRental : trimmedRentals) {
                if(rental.getId().equals(trimmedRental.getId())) {
                    matchingRentals.add(rental);
                }
            }
        }
        return matchingRentals;
    }

    private String getValidatedPolicy (String policy) {
        //If policy is not null...
        if(policy != null) {
            //If empty, return default txt.
            if(policy.trim().isEmpty()) {
                return "Default policy txt";
                //Else, return dto value
            } else {
                return policy;
            }

        } else {
            //If policy is null
            return "Default policy txt";
        }
    }


    // https://chatgpt.com/share/67b4a4fb-a588-800b-9894-16722dd3a37d
    private boolean isPeriodMatching(Period period, LocalDate startDate, LocalDate endDate) {
        boolean overlap = (startDate.isEqual(period.getStartDate()) || startDate.isAfter(period.getStartDate())) &&
                (endDate.isEqual(period.getEndDate()) || endDate.isBefore(period.getEndDate()));
        return overlap;
    }


    private boolean isAmenitiesMatching(List<Amenity> amenitiesRental, List<Amenity> amenitiesDTO) {
        Boolean match = false;
        Integer count = 0;
        if (amenitiesRental.size() < amenitiesDTO.size()) {
            System.out.println(amenitiesRental.size() + " (>=) " + amenitiesDTO.size());
            System.out.println("--------------------------------------------------------");
            return match = false;
        }
        for (Amenity amenityDTO : amenitiesDTO) {
                for (Amenity amenity : amenitiesRental) {
                    if (amenity.getAmenityTitle().equals(amenityDTO.getAmenityTitle())) {
                        System.out.println(amenity.getAmenityTitle() + " ---> MATCH <--- " + amenityDTO.getAmenityTitle());
                        match = true;
                        count++;
                        System.out.println("-> " + count + " & True <-");
                        if (count == amenitiesDTO.size()) {
                            System.out.println("-> Break & " + count + " <-");
                            break;
                        }
                    } else if (count != amenitiesDTO.size()) {
                        System.out.println("-> No Match & False <-");
                        match = false;
                    }
                }
        }
        System.out.println("--------------------------------------------------------");
        return match;
    }

    private GetRentalsResponse convertToGetRentalsResponse(Rental rental) {
        return new GetRentalsResponse(
                rental.getTitle(),
                rental.getCategory(),
                rental.getCapacity(),
                rental.getPricePerNight(),
                rental.getAddress().getCountry(),
                rental.getAddress().getCity(),
                rental.getRating().getAverageRating());
    }

}

