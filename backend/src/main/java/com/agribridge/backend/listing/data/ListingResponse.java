package com.agribridge.backend.listing.data;

public class ListingResponse {
    private boolean success;
    private String message;
    private Object data;

    public ListingResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Object getData() { return data; }
}