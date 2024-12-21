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
        log.info("Создаётся фильм: {}", film.getName());
        Film filmCreated = filmService.create(film);
        log.info("Фильм: {} создан", filmCreated.getName());
        return filmCreated;
    }

    @PutMapping
    public Film update(
            @RequestBody
            @Validated(Updated.class)
            Film newFilm) {
        log.info("Обновляется фильм {}", newFilm.getName());
        Film filmUpdated = filmService.update(newFilm);
        log.info("Фильм {} обновлён", filmUpdated.getName());
        return filmUpdated;
    }

    @DeleteMapping
    public void deleteById(@RequestParam("id") long id) {
        log.info("Удаляется фильм с id={}", id);
        filmService.deleteById(id);
        log.info("Фильм с id={} удалён", id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void like(
            @PathVariable long id,
            @PathVariable long userId) {
        log.info("Пользователь {} лайкает фильм {}", userId, id);
        filmService.like(id, userId);
        log.info("Пользователь {} лайкнул фильм {}", userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlike(
            @PathVariable long id,
            @PathVariable long userId) {
        log.info("Пользователь {} дизлайкает фильм {}", userId, id);
        filmService.unlike(id, userId);
        log.info("Пользователь {} дизлайкнул фильм {}", userId, id);
    }

    @GetMapping("/popular")
    public Collection<Film> popularFilms(
            @RequestParam(defaultValue = "10") Integer count) {
        log.info("Список первых {} популярных фильмов", count);
        return filmService.popularFilms(count);
    }


}