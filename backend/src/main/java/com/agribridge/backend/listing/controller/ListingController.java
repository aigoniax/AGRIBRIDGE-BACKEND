package com.agribridge.backend.listing.controller;

import com.agribridge.backend.listing.data.ListingRequest;
import com.agribridge.backend.listing.data.ListingResponse;
import com.agribridge.backend.listing.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listings")
@CrossOrigin(origins = "http://localhost:3000")
public class ListingController {

    @Autowired
    private ListingService listingService;

    // GET all active listings (buyers browse)
    @GetMapping
    public ResponseEntity<?> getAllListings(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(listingService.getAllListings(search, category));
    }

    // GET farmer's own listings
    @GetMapping("/my-listings")
    public ResponseEntity<?> getMyListings(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(listingService.getMyListings(token));
    }

    // POST create new listing
    @PostMapping
    public ResponseEntity<?> createListing(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ListingRequest request) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(listingService.createListing(token, request));
    }

    // PUT update listing
    @PutMapping("/{id}")
    public ResponseEntity<?> updateListing(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @RequestBody ListingRequest request) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(listingService.updateListing(token, id, request));
    }

    // DELETE listing (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListing(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(listingService.deleteListing(token, id));
    }
}