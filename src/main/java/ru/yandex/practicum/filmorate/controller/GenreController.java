package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
@Slf4j
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public Collection<Genre> findAll() {
        log.info("Find all genres");
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable long id) {
        return genreService.get(id);
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