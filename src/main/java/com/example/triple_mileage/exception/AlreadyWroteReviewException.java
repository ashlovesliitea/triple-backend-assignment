package com.example.triple_mileage.exception;

public class AlreadyWroteReviewException extends Exception{
    public AlreadyWroteReviewException() {
        super();
    }

    public AlreadyWroteReviewException(String message) {
        super(message);
    }

    public AlreadyWroteReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyWroteReviewException(Throwable cause) {
        super(cause);
    }
}
