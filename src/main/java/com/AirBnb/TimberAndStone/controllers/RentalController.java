package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dto.RentalDTO;
import com.AirBnb.TimberAndStone.dto.RentalResponse;
import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rental")
public class RentalController {


    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }


    @PostMapping
    public ResponseEntity<?> createRental(@Valid @RequestBody RentalDTO rentalDTO) {
        RentalResponse rentalResponse = rentalService.createRental(rentalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalResponse);
    }

    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@Valid @PathVariable String id) {
        Rental rental = rentalService.getRentalById(id);
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Rental>> getRentalsByCategory(@Valid @PathVariable Category category) {
        List <Rental> rentals = rentalService.getRentalsByCategory(category);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/pricepernight")
    public ResponseEntity<List<Rental>> getRentalsByPricePerNightRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        List<Rental> rentals = rentalService.getRentalsByPricePerNightRange(minPrice, maxPrice);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Rental> patchRentalById(@PathVariable String id, @RequestBody Rental rental) {
        return ResponseEntity.ok(rentalService.patchRentalById(id, rental));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable String id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






}











