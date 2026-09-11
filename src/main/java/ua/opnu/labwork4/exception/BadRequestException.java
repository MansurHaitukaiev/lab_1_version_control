package ua.opnu.labwork4.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}