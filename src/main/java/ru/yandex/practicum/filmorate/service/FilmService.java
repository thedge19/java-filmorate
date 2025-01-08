package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(long id) {
        return verifyingTheFilmsExistence(id);
    }

    public Film create(Film film) {
        if (film.getMpa().getId() > 5) {
            log.warn("Некорректный id мра: {}", film.getMpa().getId());
            throw new ValidationException("Некорректный мра");
        }
        for (Genre genre : film.getGenres()) {
            if (genre.getId() > 6) {
                log.warn("Некорректный id жанра: {}", film.getMpa().getId());
                throw new ValidationException("Некорректный жанр");
            }
        }

        return filmStorage.create(film);
    }

    public Film update(Film newFilm) {
        // проверяем необходимые условия
        Film oldFilm = verifyingTheFilmsExistence(newFilm.getId());
        if (newFilm.getName() != null) {
            oldFilm.setName(newFilm.getName());
        }
        if (newFilm.getDescription() != null) {
            oldFilm.setDescription(newFilm.getDescription());
        }
        if (newFilm.getReleaseDate() != null) {
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
        }
        if (newFilm.getDuration() != null) {
            oldFilm.setDuration(newFilm.getDuration());
        }
        // если публикация найдена и все условия соблюдены, обновляем её содержимое
        return filmStorage.update(oldFilm);
    }

    public void deleteById(long id) {
        verifyingTheFilmsExistence(id);
        filmStorage.deleteById(id);
    }

    public void like(long id, long userId) {
        log.info("Провалились в сервис");
//        if (findById(id) == null || userStorage.findById(userId) == null) {
//            log.error("Пользователь с id={} или фильм с id={} не найден like", userId, id);
//            throw new NotFoundException("Фильм не найден");
//        }
        log.info("Ищется фильм {}", id);
        Film likedFilm = filmStorage.findById(id);
        log.info("Фильм {} найден", likedFilm);
        filmStorage.like(likedFilm, userId);
    }

    public void unlike(long id, long userId) {
        if (filmStorage.findById(id) == null || userStorage.findById(userId) == null) {
            log.error("Пользователь с id={} или фильм с id={} не найден unlike", userId, id);
            throw new NotFoundException("Фильм не найден");
        }
        Film unlikedFilm = findById(id);
        filmStorage.unlike(unlikedFilm, userId);
    }

    public Collection<Film> popularFilms(Integer count) {
        List<Film> filmList = new ArrayList<>(findAll());
        filmList.sort(Comparator.comparingInt(f -> -f.getLikedUsersIds().size()));

        if (filmList.size() > count) {
            filmList = filmList.stream().limit(count).collect(Collectors.toList());
        }

        return filmList;
    }

    private Film verifyingTheFilmsExistence(long id) {
        Film film = filmStorage.findById(id);
        if (film == null) {
            throw new NotFoundException("Пользователь с id= " + id + " не найден");
        }
        return film;
    }
}
