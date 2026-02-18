package com.agribridge.agribridge_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "produce_name", nullable = false)
    private String produceName;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Freshness freshness;

    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ListingStatus status;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public enum Freshness {
        TODAY,
        ONE_TO_TWO_DAYS,
        THREE_PLUS_DAYS
    }

    public enum ListingStatus {
        AVAILABLE,
        OUT_OF_STOCK,
        DELETED
    }

    @PrePersist
    protected void onCreate() {
        postedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = ListingStatus.AVAILABLE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}