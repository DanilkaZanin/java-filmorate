package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Long, Film> films = new HashMap<>();
    //private static final LocalDate EARLIEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);

        log.info("Film created: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);

            log.info("Film updated: {}", film);
            return film;

        } else {
            log.info("Film update failed with noSuchElementException: {}", film);
            throw new NoSuchElementException("Не удалось найти фильм!");
        }
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Films list was got");
        return new ArrayList<>(films.values());
    }

    private long getNextId() {
        long currentMaxId = films.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}
