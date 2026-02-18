package com.agribridge.agribridge_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private ErrorDetails error;
    private LocalDateTime timestamp;

    // Success response
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setError(null);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

    // Error response
    public static <T> ApiResponse<T> error(String code, String message, Object details) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setData(null);
        response.setError(new ErrorDetails(code, message, details));
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

    @Data
    @NoArgsConstructor
    public static class ErrorDetails {
        private String code;
        private String message;
        private Object details;

        public ErrorDetails(String code, String message, Object details) {
            this.code = code;
            this.message = message;
            this.details = details;
        }
    }
}