package com.agribridge.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @Column(name = "listing_id", nullable = false)
    private Long listingId;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(nullable = false)
    private Double quantity;

    @Column(name = "price_per_unit", nullable = false)
    private Double pricePerUnit;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "buyer_notes")
    private String buyerNotes;

    @Column(name = "cancelled_by")
    private String cancelledBy;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    // Transient fields for response enrichment
    @Transient private String produceName;
    @Transient private String photoBase64;
    @Transient private String farmerName;
    @Transient private String farmerPhone;
    @Transient private String buyerName;
    @Transient private String buyerPhone;
    @Transient private String category;
    @Transient private String unit;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getOrderNumber() { return orderNumber; }
    public Long getListingId() { return listingId; }
    public Long getBuyerId() { return buyerId; }
    public Double getQuantity() { return quantity; }
    public Double getPricePerUnit() { return pricePerUnit; }
    public Double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getPickupLocation() { return pickupLocation; }
    public String getBuyerNotes() { return buyerNotes; }
    public String getCancelledBy() { return cancelledBy; }
    public String getCancellationReason() { return cancellationReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public LocalDateTime getCancelledAt() { return cancelledAt; }
    public String getProduceName() { return produceName; }
    public String getPhotoBase64() { return photoBase64; }
    public String getFarmerName() { return farmerName; }
    public String getFarmerPhone() { return farmerPhone; }
    public String getBuyerName() { return buyerName; }
    public String getBuyerPhone() { return buyerPhone; }
    public String getCategory() { return category; }
    public String getUnit() { return unit; }

    public void setId(Long id) { this.id = id; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public void setListingId(Long listingId) { this.listingId = listingId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public void setPricePerUnit(Double pricePerUnit) { this.pricePerUnit = pricePerUnit; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status) { this.status = status; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setBuyerNotes(String buyerNotes) { this.buyerNotes = buyerNotes; }
    public void setCancelledBy(String cancelledBy) { this.cancelledBy = cancelledBy; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    public void setCancelledAt(LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; }
    public void setProduceName(String produceName) { this.produceName = produceName; }
    public void setPhotoBase64(String photoBase64) { this.photoBase64 = photoBase64; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }
    public void setFarmerPhone(String farmerPhone) { this.farmerPhone = farmerPhone; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }
    public void setBuyerPhone(String buyerPhone) { this.buyerPhone = buyerPhone; }
    public void setCategory(String category) { this.category = category; }
    public void setUnit(String unit) { this.unit = unit; }
}