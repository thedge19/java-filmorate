package ru.yandex.practicum.filmorate.storage.classes;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    public void deleteById(long id) {
        films.remove(id);
    }

    @Override
    public void like(Film likedFilm, long userId) {
        likedFilm.getLikedUsersIds().add(userId);
    }

    @Override
    public void unlike(Film unlikedFilm, long userId) {
        unlikedFilm.getLikedUsersIds().remove(userId);
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


