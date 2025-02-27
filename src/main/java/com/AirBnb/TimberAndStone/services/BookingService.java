package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.dto.*;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.*;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
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
        Rental rental = rentalService.getRentalById(bookingRequest.getRental().getId());
        booking.setRental(rental);

        //DTO values
        Period period = new Period();
        period.setStartDate(bookingRequest.getStartDate());
        period.setEndDate(bookingRequest.getEndDate());
        booking.setPeriod(period);
        booking.setNote(bookingRequest.getNote());
        validateNumberOfGuests(booking.getRental(), bookingRequest.getNumberOfGuests());
        booking.setNumberOfGuests(bookingRequest.getNumberOfGuests());

        //Autovalues
        booking.setTotalPrice(periodService.getAmountOfDays(period) * rental.getPricePerNight());
        booking.setPaid(false);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setBookingNumber(generateBookingNumber());

        bookingRepository.save(booking);

        return new PostBookingResponse("Rental has been booked successfully",
                booking.getRental().getTitle(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getNote(),
                booking.getBookingStatus());
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
    //Can have more validation later, for ex. checking uniqueness in combination with rental/user.
    private String generateBookingNumber() {
        SecureRandom secureRandom = new SecureRandom();
        Integer randomPositiveLong = Math.abs(secureRandom.nextInt());
        return randomPositiveLong.toString();
    }
}
