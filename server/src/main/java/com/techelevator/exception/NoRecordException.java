package com.techelevator.exception;

public class NoRecordException extends RuntimeException{
    public NoRecordException() {
    }

    public NoRecordException(String message) {
        super(message);
    }

    public NoRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRecordException(Throwable cause) {
        super(cause);
    }
}
