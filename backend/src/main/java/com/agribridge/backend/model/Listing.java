package com.agribridge.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farmer_id", nullable = false)
    private Long farmerId;

    @Column(name = "produce_name", nullable = false)
    private String produceName;

    @Column(nullable = false)
    private String category;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(columnDefinition = "bytea")
    private byte[] photo;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String freshness;

    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @Column(nullable = false)
    private String status = "AVAILABLE";

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Transient
    private String farmerName;

    @Transient
    private String farmerPhone;

    @Transient
    private String farmerLocation;

    @Transient
    private String photoBase64;

    @PrePersist
    public void prePersist() {
        postedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getFarmerId() { return farmerId; }
    public String getProduceName() { return produceName; }
    public String getCategory() { return category; }
    public String getPhotoUrl() { return photoUrl; }
    public byte[] getPhoto() { return photo; }
    public Double getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public Double getPrice() { return price; }
    public String getFreshness() { return freshness; }
    public String getPickupLocation() { return pickupLocation; }
    public String getAdditionalNotes() { return additionalNotes; }
    public String getStatus() { return status; }
    public LocalDateTime getPostedAt() { return postedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public String getFarmerName() { return farmerName; }
    public String getFarmerPhone() { return farmerPhone; }
    public String getFarmerLocation() { return farmerLocation; }
    public String getPhotoBase64() { return photoBase64; }

    public void setId(Long id) { this.id = id; }
    public void setFarmerId(Long farmerId) { this.farmerId = farmerId; }
    public void setProduceName(String produceName) { this.produceName = produceName; }
    public void setCategory(String category) { this.category = category; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public void setPhoto(byte[] photo) { this.photo = photo; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public void setUnit(String unit) { this.unit = unit; }
    public void setPrice(Double price) { this.price = price; }
    public void setFreshness(String freshness) { this.freshness = freshness; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }
    public void setStatus(String status) { this.status = status; }
    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }
    public void setFarmerPhone(String farmerPhone) { this.farmerPhone = farmerPhone; }
    public void setFarmerLocation(String farmerLocation) { this.farmerLocation = farmerLocation; }
    public void setPhotoBase64(String photoBase64) { this.photoBase64 = photoBase64; }
}