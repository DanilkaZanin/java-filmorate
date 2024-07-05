package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Set;

public interface FilmStorage {
    void add(Film film);

    void delete(Film film);

    void update(Film film);

    Film getFilm(long id);

    Collection<Film> getFilms();

    boolean contains(long id);

    Set<Long> getKeys();
}
