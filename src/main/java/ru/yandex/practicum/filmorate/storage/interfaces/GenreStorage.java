package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreStorage {
    Collection<Genre> findAll();

    Genre findById(long id);

    boolean genreExists(long id);

    List<Long> genreIds(long id);

    List<Genre> getFilmGenres(List<Long> genreIds);
}
