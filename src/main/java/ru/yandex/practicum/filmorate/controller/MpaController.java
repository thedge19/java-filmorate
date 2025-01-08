package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@Slf4j
@RequiredArgsConstructor
public class MpaController {

    private final MpaService mpaService;

    @GetMapping
    public Collection<Mpa> findAll() {
        return mpaService.findAll();
    }

    @GetMapping("/{id}")
    public Mpa findNameById(@PathVariable long id) {
        return mpaService.findNameById(id);
    }

//    @PostMapping
//    public Film create(
//            @RequestBody
//            @Validated(Created.class)
//            Film film) {
//        log.info("Создаётся фильм: {}", film);
//        Film filmCreated = filmService.create(film);
//        log.info("Фильм: {} создан", filmCreated);
//        return filmCreated;
//    }

//    @PutMapping
//    public Film update(
//            @RequestBody
//            @Validated(Updated.class)
//            Film newFilm) {
//        log.info("Обновляется фильм {}", newFilm);
//        Film filmUpdated = filmService.update(newFilm);
//        log.info("Фильм {} обновлён", filmUpdated.getName());
//        return filmUpdated;
//    }
//
//    @DeleteMapping
//    public void deleteById(@RequestParam("id") long id) {
//        log.info("Удаляется фильм с id={}", id);
//        filmService.deleteById(id);
//        log.info("Фильм с id={} удалён", id);
//    }
}