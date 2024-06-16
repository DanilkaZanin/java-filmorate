package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Long, Film> films = new HashMap<>();
    private static final LocalDate EARLIEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    /*
    название не может быть пустым;
    максимальная длина описания — 200 символов;
    дата релиза — не раньше 28 декабря 1895 года;
    продолжительность фильма должна быть положительным числом. */

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        film.setId(getNextId());
        if (checkDescriptionLength(film.getDescription()) && checkReleaseDate(film.getReleaseDate())
                && checkDuration(film.getDuration()) && checkFilmName(film.getName())) {
            films.put(film.getId(), film);

            log.info("Film crated {}", film);
            return film;
        } else {
            log.info("Film validation exception");
            throw new ValidationException("Проверьте корректность полей!");
        }
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Началось добавление фильма!");
        if (films.containsKey(film.getId())) {
            if (checkDescriptionLength(film.getDescription()) && checkReleaseDate(film.getReleaseDate())
                    && checkDuration(film.getDuration()) && checkFilmName(film.getName())) {
                films.put(film.getId(), film);

                log.info("Film updated {}", film);
                return film;
            } else {
                log.info("Film validation exception");
                throw new ValidationException("Проверьте корректность полей!");
            }
        } else {
            log.info("Не удалось найти фильм");
            throw new NoSuchElementException("Не удалось найти фильм!");
        }
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Films list was got");
        return new ArrayList<>(films.values());
    }

    private boolean checkDescriptionLength(String description) {
        return description.length() <= 200;
    }

    private boolean checkReleaseDate(LocalDate releaseDate) {
        return releaseDate.isAfter(EARLIEST_RELEASE_DATE);
    }

    private boolean checkDuration(Integer duration) {
        return duration > 0;
    }

    private boolean checkFilmName(String filmName) {
        return !Objects.equals(filmName, "");
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
