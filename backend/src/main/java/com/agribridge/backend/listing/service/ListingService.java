package com.agribridge.backend.listing.service;

import com.agribridge.backend.JwtUtil;
import com.agribridge.backend.listing.data.ListingRequest;
import com.agribridge.backend.listing.data.ListingResponse;
import com.agribridge.backend.model.Listing;
import com.agribridge.backend.repository.ListingRepository;
import com.agribridge.backend.model.User;
import com.agribridge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Get all active listings
    public ListingResponse getAllListings(String search, String category) {
        List<Listing> listings;
        if (search != null && !search.isEmpty()) {
            listings = listingRepository.searchByProduceName(search);
        } else if (category != null && !category.isEmpty()) {
            listings = listingRepository.findByCategory(category);
        } else {
            listings = listingRepository.findAllActive();
        }

        // Attach farmer info to each listing
        for (Listing listing : listings) {
            userRepository.findById(listing.getFarmerId()).ifPresent(farmer -> {
                listing.setFarmerName(farmer.getFullName());
                listing.setFarmerPhone(farmer.getPhone());
                listing.setFarmerLocation(farmer.getLocation());
            });
        }

        return new ListingResponse(true, "Listings fetched successfully", listings);
    }

    // Get listings by farmer
    public ListingResponse getMyListings(String token) {
        String email = jwtUtil.extractEmail(token);
        User farmer = userRepository.findByEmail(email).orElse(null);
        if (farmer == null) {
            return new ListingResponse(false, "User not found", null);
        }
        List<Listing> listings = listingRepository.findByFarmerId(farmer.getId());
        return new ListingResponse(true, "Listings fetched successfully", listings);
    }

    // Create listing
    public ListingResponse createListing(String token, ListingRequest request) {
        String email = jwtUtil.extractEmail(token);
        User farmer = userRepository.findByEmail(email).orElse(null);

        if (farmer == null || !"FARMER".equalsIgnoreCase(farmer.getRole())) {
            return new ListingResponse(false, "Only approved farmers can create listings", null);
        }

        Listing listing = new Listing();
        listing.setFarmerId(farmer.getId());
        listing.setProduceName(request.getProduceName());
        listing.setCategory(request.getCategory());
        listing.setQuantity(request.getQuantity());
        listing.setUnit(request.getUnit());
        listing.setPrice(request.getPrice());
        listing.setFreshness(request.getFreshness());
        listing.setPickupLocation(request.getPickupLocation());
        listing.setAdditionalNotes(request.getAdditionalNotes());
        listing.setStatus("AVAILABLE");

        listingRepository.save(listing);
        return new ListingResponse(true, "Listing created successfully", listing);
    }

    // Update listing
    public ListingResponse updateListing(String token, Long id, ListingRequest request) {
        String email = jwtUtil.extractEmail(token);
        User farmer = userRepository.findByEmail(email).orElse(null);
        Listing listing = listingRepository.findById(id).orElse(null);

        if (listing == null || listing.getDeletedAt() != null) {
            return new ListingResponse(false, "Listing not found", null);
        }
        if (farmer == null || !listing.getFarmerId().equals(farmer.getId())) {
            return new ListingResponse(false, "Unauthorized", null);
        }

        listing.setProduceName(request.getProduceName());
        listing.setCategory(request.getCategory());
        listing.setQuantity(request.getQuantity());
        listing.setUnit(request.getUnit());
        listing.setPrice(request.getPrice());
        listing.setFreshness(request.getFreshness());
        listing.setPickupLocation(request.getPickupLocation());
        listing.setAdditionalNotes(request.getAdditionalNotes());

        listingRepository.save(listing);
        return new ListingResponse(true, "Listing updated successfully", listing);
    }

    // Delete listing (soft delete)
    public ListingResponse deleteListing(String token, Long id) {
        String email = jwtUtil.extractEmail(token);
        User farmer = userRepository.findByEmail(email).orElse(null);
        Listing listing = listingRepository.findById(id).orElse(null);

        if (listing == null) {
            return new ListingResponse(false, "Listing not found", null);
        }
        if (farmer == null || !listing.getFarmerId().equals(farmer.getId())) {
            return new ListingResponse(false, "Unauthorized", null);
        }

        listing.setDeletedAt(LocalDateTime.now());
        listingRepository.save(listing);
        return new ListingResponse(true, "Listing deleted successfully", null);
    }
}