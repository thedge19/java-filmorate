package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> findAll();

    Film findById(long id);

    Film create(Film film);

    Film update(Film newFilm);

    void deleteById(long id);

    void like(Film likedFilm, long userId);

    void unlike(Film unlikedFilm, long userId);
}
