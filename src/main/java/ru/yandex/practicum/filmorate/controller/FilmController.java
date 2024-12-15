package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;
    private final UserService userService;
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    public Film create(
            @RequestBody
            @Validated(Created.class)
            Film film) {
        return service.create(film);
    }

    @PutMapping
    public Film update(
            @RequestBody
            @Validated(Updated.class)
            Film newFilm) {
        // проверяем необходимые условия
        return service.update(newFilm);
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

    @GetMapping("/popular?count={count}")
    public Collection<Film> popularFilms(
            @PathVariable Integer count) {
         return filmService.popularFilms(count);
    }
}