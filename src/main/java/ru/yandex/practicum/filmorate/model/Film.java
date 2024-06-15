package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Film.
 */
@Data
public class Film {
    @NonNull
    String name;

    int id;
    String description;
    LocalDateTime releaseDate;
    Duration duration;
}
