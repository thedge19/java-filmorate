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
        filmController.create(new Film());
        filmController.create(new Film());
        filmController.create(new Film());
        userController.create(new User());
        userController.create(new User());
        userController.create(new User());
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
