package com.agribridge.backend.listing.data;

public class ListingRequest {
    private String produceName;
    private String category;
    private Double quantity;
    private String unit;
    private Double price;
    private String freshness;
    private String pickupLocation;
    private String additionalNotes;

    public String getProduceName() { return produceName; }
    public String getCategory() { return category; }
    public Double getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public Double getPrice() { return price; }
    public String getFreshness() { return freshness; }
    public String getPickupLocation() { return pickupLocation; }
    public String getAdditionalNotes() { return additionalNotes; }

    public void setProduceName(String produceName) { this.produceName = produceName; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public void setUnit(String unit) { this.unit = unit; }
    public void setPrice(Double price) { this.price = price; }
    public void setFreshness(String freshness) { this.freshness = freshness; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }
}