package com.agribridge.backend.order.data;

public class OrderRequest {
    private Long listingId;
    private Double quantity;
    private String buyerNotes;

    public Long getListingId() { return listingId; }
    public Double getQuantity() { return quantity; }
    public String getBuyerNotes() { return buyerNotes; }

    public void setListingId(Long listingId) { this.listingId = listingId; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public void setBuyerNotes(String buyerNotes) { this.buyerNotes = buyerNotes; }
}