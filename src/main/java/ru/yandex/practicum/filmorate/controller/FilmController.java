package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    /*
    название не может быть пустым;
    максимальная длина описания — 200 символов;
    дата релиза — не раньше 28 декабря 1895 года;
    продолжительность фильма должна быть положительным числом. */

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        if(checkDescriptionLength(film.getDescription()) && checkReleaseDate(film.getReleaseDate())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Проверьте корректность полей!");
        }
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if(films.containsKey(film.getId())) {
            if(checkDescriptionLength(film.getDescription()) && checkReleaseDate(film.getReleaseDate())) {
                films.put(film.getId(), film);
                return film;
            } else {
                throw new ValidationException("Проверьте корректность полей!");
            }
        } else {
            throw new NoSuchElementException("Не удалось найти фильм!");
        }
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    private boolean checkDescriptionLength(String description) {
        return description.length() <= 200;
    }

    private boolean checkReleaseDate(LocalDateTime releaseDate) {
        return releaseDate.isAfter(LocalDateTime.of(1895,12,28,0,0));
    }

    /*private boolean checkDuration(Duration duration) {

    }*/
}
