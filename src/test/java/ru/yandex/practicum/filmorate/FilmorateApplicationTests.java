package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@SpringBootTest
class FilmorateApplicationTests {

    FilmController filmController;
    UserController userController;
    ArrayList<Film> filmsToAdd;
    ArrayList<User> usersToAdd;

    @BeforeEach
    void setUp() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));
        userController = new UserController(new UserService(new InMemoryUserStorage()));
        filmsToAdd = new ArrayList<>();
        usersToAdd = new ArrayList<>();

        Film film1 = new Film();
        film1.setName("film1");
        film1.setDescription("film1");
        film1.setReleaseDate("2020-10-01");
        film1.setDuration(120);
        Film film2 = new Film();
        film2.setName("film2");
        film2.setDescription("film2");
        film2.setReleaseDate("2020-10-02");
        film2.setDuration(120);
        Film film3 = new Film();
        film3.setName("film3");
        film3.setDescription("film3");
        film3.setReleaseDate("2020-10-03");
        film3.setDuration(120);

        User user1 = new User();
        user1.setLogin("u1");
        user1.setName("user1");
        user1.setEmail("user1@email.com");
        user1.setBirthday("2000-01-01");
        User user2 = new User();
        user2.setLogin("u2");
        user2.setName("user2");
        user2.setEmail("user2@email.com");
        user2.setBirthday("2000-02-02");
        User user3 = new User();
        user3.setLogin("u3");
        user3.setName("user3");
        user3.setEmail("user3@email.com");
        user3.setBirthday("2000-03-03");

        filmController.create(film1);
        filmController.create(film2);
        filmController.create(film3);
        userController.create(user1);
        userController.create(user2);
        userController.create(user3);
    }

    @Test
    void shouldGetFilms() {
        Collection<Film> films = filmController.findAll();
        assertEquals(films.size(), 3);
    }

    @Test
    void shouldAddFilm() {
        Film film4 = new Film();
        filmController.create(film4);
        assertEquals(filmController.findAll().size(), 4);
    }

    @Test
    void shouldGetUsers() {
        Collection<User> users = userController.findAll();
        assertEquals(users.size(), 3);
    }

    @Test
    void shouldAddUser() {
        User user4 = new User();
        userController.create(user4);
        assertEquals(userController.findAll().size(), 4);
    }
}
