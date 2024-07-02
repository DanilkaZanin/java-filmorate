package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    void add(Film film);

    void delete(Film film);

    void update(Film film);
}
