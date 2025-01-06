package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.classes.FriendsDbStorage;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@SpringBootTest
class FilmorateApplicationFilmControllerTests {

    FilmController filmController;
    UserController userController;

    UserStorage userStorage = new InMemoryUserStorage();
    FriendsStorage friendsStorage = new FriendsDbStorage(new JdbcTemplate());

    @BeforeEach
    void setUp() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), userStorage));
        userController = new UserController(new UserService(userStorage, friendsStorage));

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

    @Test
    void shouldLikeFilm() {
        addValidFilm();
        addValidUser();
        filmController.like(1, 1);

        assertEquals(filmController.findFilmById(1).getLikedUsersIds().size(), 1);
    }

    @Test
    void shouldUnlikeFilm() {
        addValidFilm();
        addValidUser();
        filmController.like(1, 1);

        assertEquals(filmController.findFilmById(1).getLikedUsersIds().size(), 1);

        filmController.unlike(1, 1);

        assertEquals(filmController.findFilmById(1).getLikedUsersIds().size(), 0);
    }

    @Test
    void shouldGetPopularFilms() {
        addValidFilm();
        addValidUser();
        filmController.like(2, 1);

        String popularFilm = Objects.requireNonNull(filmController.popularFilms(2).stream().findFirst().orElse(null)).getName();
        assertEquals(popularFilm, "film2");
    }

    void addValidFilm() {
        Film film2 = new Film();
        film2.setName("film2");
        film2.setDescription("film2");
        film2.setReleaseDate("2020-10-01");
        film2.setDuration(120);

        filmController.create(film2);
    }

    void addValidUser() {
        User user2 = new User();
        user2.setLogin("u2");
        user2.setName("user2");
        user2.setEmail("user2@email.com");
        user2.setBirthday("2000-01-01");

        userController.create(user2);
    }
}
