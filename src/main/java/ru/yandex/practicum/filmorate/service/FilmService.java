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

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreService genreService;
    private final MpaService mpaService;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(long id) {
        log.info("Ищется фильм: {}", id);
        Film film = verifyingTheFilmsExistence(id);
        log.info("Фильм найден: {}", film);
        return film;
    }

    public Film create(Film film) {
        log.info("Создаётся фильм: {}", film);
        if (!mpaService.mpaExists(film.getMpa().getId())) {
            log.warn("Некорректный id мра: {}", film.getMpa().getId());
            throw new ValidationException("Некорректный мра");
        }
        if (!film.getGenres().isEmpty()) {
            for (Genre genre : film.getGenres()) {
                genreService.checkGenre(genre.getId());
            }
        }
        Film createdFilm = filmStorage.create(film);
        log.info("Создан фильм: {}", createdFilm);
        return createdFilm;
    }

    public Film update(Film newFilm) {
        Film oldFilm = verifyingTheFilmsExistence(newFilm.getId());
        log.info("Обновляется фильм {}", newFilm);
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
        if (!newFilm.getGenres().isEmpty()) {
            for (Genre genre : newFilm.getGenres()) {
                genreService.checkGenre(genre.getId());
            }
        }
        Film updatedFilm = filmStorage.update(oldFilm);
        log.info("Фильм {} обновлён", updatedFilm);
        return updatedFilm;
    }

    public void deleteById(long id) {
        log.info("Удаляется фильм с id : {}", id);
        verifyingTheFilmsExistence(id);
        filmStorage.deleteById(id);
    }

    public void like(long id, long userId) {
        log.info("Ищется лайкнутый фильм {}", id);
        Film likedFilm = filmStorage.findById(id);
        log.info("Фильм для лайка {} найден", likedFilm);
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
        log.info("Выводится список популярных фильмов");
        if (count == null) {
            count = 10;
        }
        return filmStorage.popularFilms(count);
    }

    private Film verifyingTheFilmsExistence(long id) {
        Film film = filmStorage.findById(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id= " + id + " не найден");
        }
        return film;
    }
}
