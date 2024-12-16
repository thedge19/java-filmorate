package ru.yandex.practicum.filmorate.storage.classes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    public Collection<Film> findAll() {
        return films.values();
    }

    public Film findById(long id) {
        if (films.get(id) == null) {
            log.error("Film {} not found", id);
            throw new NotFoundException("Фильм не найден findById");
        }
        return films.get(id);
    }

    public Film create(Film film) {
        // формируем дополнительные данные
        film.setId(getNextId(films));
        // сохраняем новую публикацию в памяти приложения
        films.put(film.getId(), film);
        log.info("Фильм {} добавлен", film.getId());
        return film;
    }

    public Film update(Film newFilm) {
        // проверяем необходимые условия
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
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
            films.put(oldFilm.getId(), oldFilm);
            log.info("Фильм {} обновлён", oldFilm.getName());
            return oldFilm;
        }
        log.error("Фильм с id = {} не найден", newFilm.getId());
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    @Override
    public void like(long id, long userId) {
        Film film = films.get(id);
        film.getLikedUsersIds().add(userId);
    }

    @Override
    public void unlike(long id, long userId) {
        Film film = films.get(id);
        film.getLikedUsersIds().remove(userId);
    }

    @Override
    public Collection<Film> popularFilms(Integer count) {
        List<Film> filmList = new ArrayList<>(films.values());
        filmList.sort(Comparator.comparingInt(f -> - f.getLikedUsersIds().size()));

        if (films.values().size() > count) {
            filmList = filmList.stream().limit(count).collect(Collectors.toList());
        }

        return filmList;
    }

    private long getNextId(Map<Long, ?> elements) {
        long currentMaxId = elements.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}


