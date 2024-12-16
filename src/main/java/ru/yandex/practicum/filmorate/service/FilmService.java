package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Collection<Film> findAll() {
        log.info("Id первого фильма = {}", filmStorage.findById(1).getId());
        return filmStorage.findAll();
    }

    public Film findById(long id) {
        return filmStorage.findById(id);
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film newFilm) {
        return filmStorage.update(newFilm);
    }

    public void like(long id, long userId) {
        if (filmStorage.findById(id) == null || userStorage.findById(userId) == null) {
            log.error("Пользователь с id={} или фильм с id={} не найден like", userId, id);
            throw new NotFoundException("Фильм не найден");
        }
        filmStorage.like(id, userId);
    }

    public void unlike(long id, long userId) {
        if (filmStorage.findById(id) == null || userStorage.findById(userId) == null) {
            log.error("Пользователь с id={} или фильм с id={} не найден unlike", userId, id);
            throw new NotFoundException("Фильм не найден");
        }
        filmStorage.unlike(id, userId);
    }

    public Collection<Film> popularFilms(Integer count) {
        return filmStorage.popularFilms(count);
    }
}
