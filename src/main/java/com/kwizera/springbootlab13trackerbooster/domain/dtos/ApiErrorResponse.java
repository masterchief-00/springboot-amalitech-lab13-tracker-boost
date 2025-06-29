package com.kwizera.springbootlab13trackerbooster.domain.dtos;

public class ApiErrorResponse {
    private int status;
    private String message;

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int status;
        private String message;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ApiErrorResponse build() {
            return new ApiErrorResponse(this.status, this.message);
        }
    }

}
