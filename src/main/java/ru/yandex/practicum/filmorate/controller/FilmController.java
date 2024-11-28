package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.logs.LogMessage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();
    private final Service service = new Service();
    private final static Logger log = LoggerFactory.getLogger(Film.class);
    private final LogMessage lMsg = new LogMessage();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        // проверяем выполнение необходимых условий
        if (film.getName() == null || film.getName().isEmpty()) {
            log.error("Название не может быть пустым");
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error(lMsg.getFilmDescription(), film.getDescription().length());
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (!service.checkDate(film.getReleaseDate(), LocalDate.parse("1895-12-27"))) {
            log.error(lMsg.getFilmRelease(), film.getReleaseDate());
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.error(lMsg.getPositiveDuration(), film.getReleaseDate());
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }

        // формируем дополнительные данные
        film.setId(service.getNextId(films));
        // сохраняем новую публикацию в памяти приложения
        films.put(film.getId(), film);
        log.info("Фильм {} добавлен", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        // проверяем необходимые условия
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            if (newFilm.getName() != null) {
                oldFilm.setName(newFilm.getName());
            }
            if (newFilm.getDescription() != null) {
                if (newFilm.getDescription().length() > 200) {
                    log.error(lMsg.getFilmDescription(), newFilm.getDescription().length());
                    throw new ValidationException("Описание не должно быть длиннее 200 символов");
                }
                oldFilm.setDescription(newFilm.getDescription());
            }
            if (newFilm.getReleaseDate() != null) {
                if (!service.checkDate(newFilm.getReleaseDate(), LocalDate.parse("1895-12-27"))) {
                    log.error(lMsg.getFilmRelease(), newFilm.getReleaseDate());
                    throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
                }
                oldFilm.setReleaseDate(newFilm.getReleaseDate());
            }
            if (newFilm.getDuration() != null) {
                if (newFilm.getDuration() <= 0) {
                    log.error(lMsg.getPositiveDuration(), newFilm.getReleaseDate());
                    throw new ValidationException("Продолжительность фильма должна быть положительным числом");
                }
                oldFilm.setDuration(newFilm.getDuration());
            }
            // если публикация найдена и все условия соблюдены, обновляем её содержимое
            films.put(oldFilm.getId(), oldFilm);
            log.info("Фильм {} обновлён", oldFilm.getName());
            return oldFilm;
        }
        log.error(lMsg.getUndiscoveredId("Фильм"), newFilm.getId());
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
    }
}
