package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.*;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.dtos.requests.booking.PatchBookingRequest;
import com.AirBnb.TimberAndStone.dtos.responses.booking.AllBookingsResponse;
import com.AirBnb.TimberAndStone.dtos.responses.booking.BookingResponse;
import com.AirBnb.TimberAndStone.dtos.responses.booking.PatchBookingResponse;
import com.AirBnb.TimberAndStone.dtos.responses.booking.PostBookingResponse;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final PeriodService periodService;
    private final RentalService rentalService;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public BookingService(BookingRepository bookingRepository, PeriodService periodService, UserService userService, RentalService rentalService, UserRepository userRepository, RentalRepository rentalRepository) {
        this.bookingRepository = bookingRepository;
        this.periodService = periodService;
        this.userService = userService;
        this.rentalService = rentalService;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public PostBookingResponse createBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();

        //Set user to authorized user.
        booking.setUser(userService.getAuthenticated());

        //Find and set rental
        Rental rental = rentalRepository.findById(bookingRequest.getRental().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
        booking.setRental(rental);

        /*
        Gets all the available periods for the rental you want to book and checks the dates you want to book vs the ->
        available dates in rental and send an error if none of the dates match
         */
        List<Period> periods = rental.getAvailablePeriods();
        periodService.isPeriodMatching(periods, bookingRequest);

        //DTO values
        Period period = new Period();
        period.setStartDate(bookingRequest.getStartDate());
        period.setEndDate(bookingRequest.getEndDate());
        booking.setPeriod(period);
        booking.setNote(bookingRequest.getNote());
        booking.setNumberOfGuests(bookingRequest.getNumberOfGuests());

        //Autovalues
        booking.setTotalPrice(periodService.getAmountOfDays(period) * rental.getPricePerNight());
        booking.setPaid(false);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setBookingNumber(generateBookingNumber(userService.getAuthenticated(), rental));
        booking.setReviewedByUser(false);
        booking.setReviewedByHost(false);

        validateBooking(booking);

        Booking createdBooking = bookingRepository.save(booking);

        return new PostBookingResponse("Rental has been booked successfully",
                createdBooking.getRental().getTitle(),
                createdBooking.getPeriod(),
                createdBooking.getTotalPrice(),
                createdBooking.getNote(),
                createdBooking.getBookingStatus());
    }

    public List<AllBookingsResponse> getAllBookings() {
        //Finds all bookings, converts to DTO and returns list.
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::convertToAllBookingsResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return convertToBookingResponse(booking);
    }

    public List<BookingResponse> getBookingsByUserId(String id){
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Booking> bookings = bookingRepository.findByUserId(id);

        return bookings.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getMyBookings() {
        User user = userService.getAuthenticated();
        List<Booking> bookings = bookingRepository.findByUserId(user.getId());

        if(bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found");
        }

        return bookings.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getBookingsByRentalId(String id) {
        rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        List<Booking> bookings = bookingRepository.findByRentalId(id);

        return bookings.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    public PatchBookingResponse patchBooking(String id, PatchBookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        User currentUser = userService.getAuthenticated();

        if (!currentUser.getId().equals(booking.getUser().getId())) {
            throw new UnauthorizedException("You do not have permission to change this booking!");
        }

        if(request.getNumberOfGuests() != null) {
            validateNumberOfGuests(booking.getRental(), request.getNumberOfGuests());
            booking.setNumberOfGuests(request.getNumberOfGuests());
        }

        if(request.getNote() != null) {
            booking.setNote(request.getNote());
        }

        if(request.getStartDate() != null && request.getEndDate() != null) {
            Period period = new Period(request.getStartDate(), request.getEndDate());
            booking.setPeriod(period);
            booking.setTotalPrice(periodService.getAmountOfDays(period) * booking.getRental().getPricePerNight());
        }

        validateBooking(booking);

        bookingRepository.save(booking);
        return convertToPatchBookingResponse("The booking has been updated successfully", booking);
    }

    public PatchBookingResponse approveBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        User currentUser = userService.getAuthenticated();

        //Check if current user is the host of this rental.
        if (!currentUser.getId().equals(booking.getRental().getHost().getId())) {
            throw new UnauthorizedException("You do not have permission to approve this booking!");
        }

        BookingStatus status = booking.getBookingStatus();

        if (status.equals(BookingStatus.APPROVED) || status.equals(BookingStatus.CONFIRMED)) {
            throw new IllegalArgumentException("Booking is already approved and/or confirmed.");
        }

        if (status.equals(BookingStatus.CANCELLED)) {
            throw new IllegalArgumentException("Booking is already cancelled and can not be approved");
        }

        booking.setBookingStatus(BookingStatus.APPROVED);

        bookingRepository.save(booking);

        return new PatchBookingResponse(
                "The booking has been approved and is now awaiting payment.",
                booking.getRental().getTitle(),
                booking.getBookingNumber(),
                booking.getUser().getUsername(),
                booking.getNumberOfGuests(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getPaid(),
                booking.getBookingStatus(),
                booking.getNote());
    }

    public PatchBookingResponse payAndConfirm(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        User currentUser = userService.getAuthenticated();

        //Check if current user is the booking user
        if (!currentUser.getId().equals(booking.getUser().getId())) {
            throw new UnauthorizedException("You do not have permission to pay and confirm this booking!");
        }

        BookingStatus status = booking.getBookingStatus();

        if (status.equals(BookingStatus.PENDING)) {
            throw new IllegalArgumentException("Booking has to be approved first!");
        } else if (status.equals(BookingStatus.CANCELLED)) {
            throw new IllegalArgumentException("Booking is already cancelled and can not be approved");
        } else if (status.equals(BookingStatus.CONFIRMED) && booking.getPaid()) {
            throw new IllegalArgumentException("Booking is already confirmed and paid!");
        }

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setPaid(true);
        bookingRepository.save(booking);
        return new PatchBookingResponse(
                "The booking has been confirmed and paid!",
                booking.getRental().getTitle(),
                booking.getBookingNumber(),
                booking.getUser().getUsername(),
                booking.getNumberOfGuests(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getPaid(),
                booking.getBookingStatus(),
                booking.getNote());
    }

    public void deleteBooking(String id) {
        //Find by id
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        //Get user and check if this is the users booking, else throw ex.
        User currentUser = userService.getAuthenticated();
        if (!currentUser.getId().equals(booking.getUser().getId())) {
            throw new UnauthorizedException("You do not have permission to change this booking!");
        }

        bookingRepository.delete(booking);
    }

    //------------------------------------------HELP METHODS----------------------------------------------------

    private void validateNumberOfGuests(Rental rental, int numberOfGuests) {
        if (numberOfGuests < 1) {
            throw new IllegalArgumentException("Number of guests must be greater than 0");
        } else if (numberOfGuests > rental.getCapacity()) {
            throw new IllegalArgumentException("This rental allows max " + rental.getCapacity() + " guests!");
        }
    }

    private void validateBooking(Booking booking) {
        if(booking.getPeriod().getStartDate().isAfter(booking.getPeriod().getEndDate()) || booking.getPeriod().getStartDate().equals(booking.getPeriod().getEndDate())) {
            throw new IllegalArgumentException("Booking period start date must be before end date!");
        }

        validateNumberOfGuests(booking.getRental(), booking.getNumberOfGuests());
    }

    private BookingResponse convertToBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getBookingNumber(),
                booking.getRental().getTitle(),
                booking.getUser().getUsername(),
                booking.getNumberOfGuests(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getBookingStatus(),
                booking.getNote(),
                booking.getCreatedAt()
        );
    }

    private AllBookingsResponse convertToAllBookingsResponse(Booking booking) {
        return new AllBookingsResponse(
                booking.getRental().getTitle(),
                booking.getUser().getUsername(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getBookingStatus()
        );
    }

    private PatchBookingResponse convertToPatchBookingResponse(String message, Booking booking) {
        return new PatchBookingResponse(
                message,
                booking.getRental().getTitle(),
                booking.getBookingNumber(),
                booking.getUser().getUsername(),
                booking.getNumberOfGuests(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getPaid(),
                booking.getBookingStatus(),
                booking.getNote()
        );
    }

    //https://www.baeldung.com/java-uuid-unique-long-generation
    //https://www.baeldung.com/java-secure-random
    //Checking uniqueness in combination with rental/user and returns a number as a String.
    private String generateBookingNumber(User user, Rental rental) {
        SecureRandom secureRandom = new SecureRandom();
        //Generate random number
        Integer randomPositiveInt = Math.abs(secureRandom.nextInt());

        // Use a magic number for testing, set a default value and make a booking with the same user and rental twice.
        // Check console for print results. Should return result 1 first time and result 2 second time.
        //randomPositiveInt = 88156710;

        System.out.println("Generated number: " + randomPositiveInt);

        //Check if there is any matches with existing booking number
        //Should return null if the booking number does not already exist.
        Booking matchingBooking = bookingRepository.findByBookingNumberAndUserIdAndRentalId(randomPositiveInt.toString(), user.getId(), rental.getId())
                .orElse(null);

        //if booking is found
        if(matchingBooking != null) {
            System.out.println("Booking number already exists in combination with user or rental!");
            String matchingNumber = matchingBooking.getBookingNumber();

            System.out.println("Matching bookingID: " + matchingBooking.getId());
            System.out.println("Matching bookingNumber: " + matchingNumber);

                while (true) {
                        //Generate a new number.
                        randomPositiveInt = Math.abs(secureRandom.nextInt());
                    System.out.println("New Generated number: " + randomPositiveInt);
                        Booking newMatch = bookingRepository.findByBookingNumberAndUserIdAndRentalId(randomPositiveInt.toString(), user.getId(), rental.getId())
                                .orElse(null);
                        if (newMatch == null) {
                            System.out.println("2: No matches found, ID is unique in combination with user and rental");
                            return randomPositiveInt.toString();
                        }
                    }
                }
        System.out.println("1: No matches found, ID is unique in combination with user and rental");
        return randomPositiveInt.toString();
    }
}
