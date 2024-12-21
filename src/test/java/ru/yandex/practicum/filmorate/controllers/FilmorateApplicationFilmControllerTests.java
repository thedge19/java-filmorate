package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryUserStorage;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@SpringBootTest
class FilmorateApplicationFilmControllerTests {

    FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));

        Film film1 = new Film();
        film1.setName("film1");
        film1.setDescription("film1");
        film1.setReleaseDate("2020-10-01");
        film1.setDuration(120);

        filmController.create(film1);
    }

    @Test
    void shouldGetFilms() {
        addValidFilm();
        Collection<Film> films = filmController.findAll();
        assertEquals(films.size(), 2);
    }

    @Test
    void shouldAddFilm() {
        addValidFilm();
        assertEquals(filmController.findAll().size(), 2);
    }

    @Test
    void shouldFindFilmById() {
        addValidFilm();
        assertEquals(filmController.findFilmById(1).getName(), "film1");
        assertEquals(filmController.findFilmById(2).getDescription(), "film2");
    }

    @Test
    void shouldUpdateFilm() {
        Film newFilm = new Film();
        newFilm.setId(1L);
        newFilm.setName("film2");
        newFilm.setDescription("film2");

        filmController.update(newFilm);

        assertEquals(filmController.findFilmById(1).getName(), "film2");
    }

    @Test
    void shouldRemoveFilm() {
        filmController.deleteById(1);
        assertEquals(filmController.findAll().size(), 0);
    }

    void addValidFilm() {
        Film film2 = new Film();
        film2.setName("film2");
        film2.setDescription("film2");
        film2.setReleaseDate("2020-10-01");
        film2.setDuration(120);

        filmController.create(film2);
    }
}
