package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;

    public Collection<Genre> findAll() {
        return genreStorage.findAll();
    }

    public Genre findById(long id) {
        log.info("Запрашивается наименование жанра с id={}", id);
        if (!genreStorage.genreExists(id)) {
            log.warn("Некорректный id жанра: {}", id);
            throw new NotFoundException("Некорректный жанр");
        }
        return genreStorage.findById(id);
    }

    public boolean genreExist(long id) {
        return genreStorage.genreExists(id);
    }
}
