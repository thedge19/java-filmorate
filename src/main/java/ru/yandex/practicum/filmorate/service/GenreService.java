package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;

    public Collection<Genre> findAll() {
        return genreStorage.findAll();
    }

    public Genre findById(long id) {
        if (genreNotExist(id)) {
            log.warn("Некорректный жанр c id={}", id);
            throw new NotFoundException("Некорректный жанр");
        }
        return genreStorage.findById(id);
    }

    public boolean genreNotExist(long id) {
        return !genreStorage.genreExists(id);
    }

    public void checkGenre(long genreId) {
        if (genreNotExist(genreId)) {
            log.warn("Некорректный id жанра: {}", genreId);
            throw new ValidationException("Некорректный жанр");
        }
    }

    public List<Genre> getFilmGenres(long id) {
        List<Long> genreIds = genreStorage.genreIds(id);
        List<Genre> genres = new ArrayList<>();

        if (!genreIds.isEmpty()) {
            genres = genreStorage.getFilmGenres(genreIds);
        }
        genres.sort(Comparator.comparing(Genre::getId));

        log.info("Лист жанров {}", genres);

        return genres;
    }
}
