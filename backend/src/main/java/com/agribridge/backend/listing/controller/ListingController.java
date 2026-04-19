package com.agribridge.backend.listing.controller;

import com.agribridge.backend.listing.data.ListingRequest;
import com.agribridge.backend.listing.data.ListingResponse;
import com.agribridge.backend.listing.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // GET single listing by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getListingById(@PathVariable Long id) {
        return ResponseEntity.ok(listingService.getListingById(id));
    }

    // GET farmer's own listings
    @GetMapping("/my-listings")
    public ResponseEntity<?> getMyListings(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(listingService.getMyListings(token));
    }

    // GET all listings for admin moderation
    @GetMapping("/admin-all")
    public ResponseEntity<?> getAllListingsForAdmin() {
        return ResponseEntity.ok(listingService.getAllListingsForAdmin());
    }

    // POST create new listing with photo
    @PostMapping
    public ResponseEntity<?> createListing(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("produceName") String produceName,
            @RequestParam("category") String category,
            @RequestParam("quantity") Double quantity,
            @RequestParam("unit") String unit,
            @RequestParam("price") Double price,
            @RequestParam("freshness") String freshness,
            @RequestParam("pickupLocation") String pickupLocation,
            @RequestParam(value = "additionalNotes", required = false) String additionalNotes,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {

        String token = authHeader.replace("Bearer ", "");
        ListingRequest request = new ListingRequest();
        request.setProduceName(produceName);
        request.setCategory(category);
        request.setQuantity(quantity);
        request.setUnit(unit);
        request.setPrice(price);
        request.setFreshness(freshness);
        request.setPickupLocation(pickupLocation);
        request.setAdditionalNotes(additionalNotes);
        return ResponseEntity.ok(listingService.createListing(token, request, photo));
    }

    // PUT update listing
    @PutMapping("/{id}")
    public ResponseEntity<?> updateListing(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @RequestParam("produceName") String produceName,
            @RequestParam("category") String category,
            @RequestParam("quantity") Double quantity,
            @RequestParam("unit") String unit,
            @RequestParam("price") Double price,
            @RequestParam("freshness") String freshness,
            @RequestParam("pickupLocation") String pickupLocation,
            @RequestParam(value = "additionalNotes", required = false) String additionalNotes,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {

        String token = authHeader.replace("Bearer ", "");
        ListingRequest request = new ListingRequest();
        request.setProduceName(produceName);
        request.setCategory(category);
        request.setQuantity(quantity);
        request.setUnit(unit);
        request.setPrice(price);
        request.setFreshness(freshness);
        request.setPickupLocation(pickupLocation);
        request.setAdditionalNotes(additionalNotes);
        return ResponseEntity.ok(listingService.updateListing(token, id, request, photo));
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