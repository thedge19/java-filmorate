package ru.yandex.practicum.filmorate.storage.classes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
        return films.get(id);
    }

    public Film create(Film film) {
        // формируем дополнительные данные
        film.setId(getNextId(films));
        // сохраняем новую публикацию в памяти приложения
        films.put(film.getId(), film);
        return film;
    }

    public Film update(Film newFilm) {
            // если публикация найдена и все условия соблюдены, обновляем её содержимое
            films.put(newFilm.getId(), newFilm);
            return newFilm;
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


