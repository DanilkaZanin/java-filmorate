package ru.yandex.practicum.filmorate.exception;

public class IsAlreadyExistsException extends RuntimeException {
    public IsAlreadyExistsException(final String message) {
        super(message);
    }
}
