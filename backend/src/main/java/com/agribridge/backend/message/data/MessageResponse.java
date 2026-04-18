package com.agribridge.backend.message.data;

public class MessageResponse {
    private boolean success;
    private String message;
    private Object data;

    public MessageResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Object getData() { return data; }
}