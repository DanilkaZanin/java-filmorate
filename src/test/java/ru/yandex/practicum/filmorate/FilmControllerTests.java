package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTests {
    private final FilmController filmController = new FilmController();
    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setName("film");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(50);
    }

    @Test
    public void shouldAddFilm(){
        filmController.createFilm(film);

        assertEquals(List.of(film),filmController.getAllFilms());
    }

    @Test
    public void shouldNotAddFilmWithEmptyName() {
        film.setName("");

        assertThrows(ValidationException.class,() -> filmController.createFilm(film));
    }

    @Test
    public void shouldAddFilmWith200SymbolsDescription() {
        String description = "Spring Type Conversion\n" +
                "The core.convert package provides a general type conversion system. " +
                "The system defines an SPI to implement type conversion logic and an " +
                "API to perform type conversions at runtim";
        film.setDescription(description);

        assertDoesNotThrow(() -> filmController.createFilm(film));
    }

    @Test
    public void shouldNotAddFilmWithMoreThen200SymbolsDescription() {
        String description = "Spring Type Conversion\n" +
                "The core.convert package provides a general type conversion system. " +
                "The system defines an SPI to implement type conversion logic and an " +
                "API to perform type conversions at runtime";
        film.setDescription(description);

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void shouldNotAddFilmWithTooEarliestDate() {
        LocalDate date = LocalDate.of(1895,12,27);
        film.setReleaseDate(date);

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void shouldNotAddFilmWithNegativeDuration() {
        film.setDuration(-1);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void shouldNotAddEmptyFilm() {
        Film f1 = new Film();

        assertThrows(NullPointerException.class, ()-> filmController.createFilm(f1));
    }

    @Test
    public void shouldUpdateFilm() {
        filmController.createFilm(film);
        film.setDescription("new desription");

        filmController.updateFilm(film);
    }

    @Test
    public void shouldNotUpdateNonExistentFilm() {
        assertThrows(NoSuchElementException.class, () -> filmController.updateFilm(film));
    }
}
