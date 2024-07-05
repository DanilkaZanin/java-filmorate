package ru.yandex.practicum.filmorate.exception;

public class IsAlreadyFriendException extends RuntimeException {
    public IsAlreadyFriendException(final String message) {
        super(message);
    }
}
