package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dto.BookingRequest;
import com.AirBnb.TimberAndStone.dto.BookingResponse;
import com.AirBnb.TimberAndStone.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        BookingResponse bookingResponse = bookingService.createBooking(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings() {
        List<BookingResponse> bookings = bookingService.getAllBookings();
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }
}
