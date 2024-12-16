package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable long id) {
        return filmService.findById(id);
    }

    @PostMapping
    public Film create(
            @RequestBody
            @Validated(Created.class)
            Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(
            @RequestBody
            @Validated(Updated.class)
            Film newFilm) {
        // проверяем необходимые условия
        return filmService.update(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public void like(
            @PathVariable long id,
            @PathVariable long userId) {
        filmService.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlike(
            @PathVariable long id,
            @PathVariable long userId) {
        filmService.unlike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> popularFilms(
            @RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.popularFilms(count);
    }
}