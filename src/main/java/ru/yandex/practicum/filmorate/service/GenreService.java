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

    public Genre get(long id) {
        log.info("Запрашивается наименование жанра с id={}", id);
        if (id > 6) {
            log.warn("Некорректный id жанра: {}", id);
            throw new NotFoundException("Некорректный жанр");
        }
        return genreStorage.get(id);
    }

//
//    public void deleteById(long id) {
//        filmStorage.deleteById(id);
//    }



//    private Film verifyingTheFilmsExistence(long id) {
//        Film film = filmStorage.findById(id);
//        if (film == null) {
//            throw new NotFoundException("Пользователь с id= " + id + " не найден");
//        }
//        return film;
//    }
}
