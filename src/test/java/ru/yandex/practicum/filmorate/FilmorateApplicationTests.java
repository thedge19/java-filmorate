package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilmorateApplicationTests {

    FilmController filmController;
    UserController userController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
        userController = new UserController();
        filmController.create(new Film(1L, "newFilm1", "newFilmDescription1", "2010-08-10", 120));
        filmController.create(new Film(2L, "newFilm2", "newFilmDescription2", "1980-10-15", 160));
        filmController.create(new Film(3L, "newFilm3", "newFilmDescription3", "2020-02-12", 100));
        userController.create(new User(1L, "user1@user.ru", "login1", "user1", "2000-05-01"));
        userController.create(new User(2L, "user2@user.ru", "login2", "user2", "1980-04-02"));
        userController.create(new User(3L, "user3@user.ru", "login3", "user3", "1970-03-08"));
    }

    @Test
    void shouldGetFilms() {
        Collection<Film> films = filmController.findAll();
        assertEquals(films.size(), 3);
    }

    @Test
    void shouldNotAddFilmBefore() {
        try {
            filmController.create(new Film(4L, "L’Arrivée d’un train en gare de la Ciotat", "newFilmDescription4", "1895-12-28", 3));
            filmController.create(new Film(5L, "Fake L’Arrivée d’un train en gare de la Ciotat", "newFilmDescription4", "1895-12-26", 3));
        } catch (ValidationException e) {
        }
        assertEquals(filmController.findAll().size(), 4);
    }

    @Test
    void shouldNotAddFilmWithNegativeDuration() {
        try {
            filmController.create(new Film(4L, "newFilm4", "newFilmDescription4", "2020-02-12", 1));
            filmController.create(new Film(5L, "newFilm5", "newFilmDescription5", "2020-02-14", -1));
        } catch (ValidationException e) {
        }
        assertEquals(filmController.findAll().size(), 4);
    }

    @Test
    void shouldGetUsers() {
        Collection<User> users = userController.findAll();
        assertEquals(users.size(), 3);
    }

    @Test
    void shouldNotAddUserWithEmailWithoutAt() {
        try {
            userController.create(new User(4L, "user4@user.ru", "login4", "user4", "1960-03-08"));
            userController.create(new User(5L, "user4.user.ru", "login4", "user4", "1950-03-08"));
        } catch (ValidationException e) {
        }
        assertEquals(userController.findAll().size(), 4);
    }

    @Test
    void shouldNotAddUnbornUser() {
        try {
            userController.create(new User(4L, "user4@user.ru", "login4", "user4", "2024-11-28"));
            userController.create(new User(5L, "user5.user.ru", "login5", "user5", "2024-11-29"));
        } catch (ValidationException e) {
        }
        assertEquals(userController.findAll().size(), 4);
    }
}
