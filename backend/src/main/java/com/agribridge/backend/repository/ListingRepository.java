package com.agribridge.backend.repository;

import com.agribridge.backend.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    // Get all active listings (not deleted)
    @Query("SELECT l FROM Listing l WHERE l.deletedAt IS NULL AND l.status = 'AVAILABLE'")
    List<Listing> findAllActive();

    // Get all listings by farmer
    @Query("SELECT l FROM Listing l WHERE l.farmerId = :farmerId AND l.deletedAt IS NULL")
    List<Listing> findByFarmerId(@Param("farmerId") Long farmerId);

    // Search by produce name
    @Query("SELECT l FROM Listing l WHERE l.deletedAt IS NULL AND l.status = 'AVAILABLE' AND LOWER(l.produceName) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Listing> searchByProduceName(@Param("search") String search);

    // Filter by category
    @Query("SELECT l FROM Listing l WHERE l.deletedAt IS NULL AND l.status = 'AVAILABLE' AND l.category = :category")
    List<Listing> findByCategory(@Param("category") String category);
}