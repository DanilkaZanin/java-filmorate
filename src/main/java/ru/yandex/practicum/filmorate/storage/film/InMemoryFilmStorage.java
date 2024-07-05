package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films;

    public InMemoryFilmStorage() {
        this.films = new HashMap<>();
    }

    @Override
    public void add(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public void delete(Film film) {
        films.remove(film.getId());
    }

    @Override
    public void update(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public Film getFilm(long id) {
        return films.get(id);
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public boolean contains(long id) {
        return films.containsKey(id);
    }

    @Override
    public Set<Long> getKeys() {
        return films.keySet();
    }
}
