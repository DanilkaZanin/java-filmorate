package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenresController {
    private final GenreService genreService;

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable("id") long id) {
        return genreService.getGenre(id);
    }

    @GetMapping
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }


}
