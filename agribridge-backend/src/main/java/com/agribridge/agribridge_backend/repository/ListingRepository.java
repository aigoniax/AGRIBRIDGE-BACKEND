package com.agribridge.agribridge_backend.repository;

import com.agribridge.agribridge_backend.model.Listing;
import com.agribridge.agribridge_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    // Find all listings by farmer
    List<Listing> findByFarmer(User farmer);

    // Find all available listings
    List<Listing> findByStatus(Listing.ListingStatus status);

    // Find listings by category
    List<Listing> findByCategoryIdAndStatus(
            Long categoryId,
            Listing.ListingStatus status
    );

    // Search listings by produce name
    List<Listing> findByProduceNameContainingIgnoreCaseAndStatus(
            String produceName,
            Listing.ListingStatus status
    );
}