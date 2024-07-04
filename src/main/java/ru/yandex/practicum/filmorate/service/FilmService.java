package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

    public Film createFilm(Film film) {
        film.setId(getNextId());
        filmStorage.add(film);

        log.info("Film created: {}", film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (filmStorage.contains(film.getId())) {
            filmStorage.update(film);

            log.info("Film updated: {}", film);
            return film;

        } else {
            log.info("Film update failed with noSuchElementException: {}", film);
            throw new NotFoundException("Не удалось найти фильм!");
        }
    }

    public List<Film> getAllFilms() {
        log.info("Films list was got");
        return new ArrayList<>(filmStorage.getFilms());
    }

    public Film setLike(long filmId, long userId) {
        if (!filmStorage.contains(filmId)) {
            throw new NotFoundException("Film with id : " + filmId + "not found");
        }
        Film film = filmStorage.getFilm(filmId);
        film.putLike(userId);
        return film;
    }

    public Film removeLike(long filmId, long userId) {
        if (!filmStorage.contains(filmId)) {
            throw new NotFoundException("Film with id : " + filmId + "not found");
        }

        Film film = filmStorage.getFilm(filmId);

        if (!film.getLikes().contains(userId)) {
            throw new NotFoundException("User with id : " + userId + "not found");
        }

        film.deleteLike(userId);
        return film;
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted(Comparator.comparing((Film film) -> film.getLikes().size()).reversed())
                .limit(count)
                .toList();
    }

    private long getNextId() {
        long currentMaxId = filmStorage.getKeys().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }

}
