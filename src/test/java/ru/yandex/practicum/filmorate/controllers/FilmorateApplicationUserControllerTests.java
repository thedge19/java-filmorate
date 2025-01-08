package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.classes.FriendsDbStorage;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@SpringBootTest
class FilmorateApplicationUserControllerTests {

    UserController userController;
    UserStorage userStorage = new InMemoryUserStorage();

    @BeforeEach
    void setUp() {
        userController = new UserController(new UserService(userStorage, new FriendsDbStorage(new JdbcTemplate())));

        User user1 = new User();
        user1.setLogin("u1");
        user1.setName("user1");
        user1.setEmail("user1@email.com");
        user1.setBirthday("2000-01-01");

        userController.create(user1);
    }

    @Test
    void shouldGetUsers() {
        addValidUser();
        addSecondValidUser();
        Collection<User> users = userController.findAll();
        assertEquals(users.size(), 3);
    }

    @Test
    void shouldFindUserById() {
        assertEquals(userController.findUserById(1).getName(), "user1");
    }


    @Test
    void shouldAddUser() {
        addValidUser();
        assertEquals(userController.findAll().size(), 2);
    }

    @Test
    void shouldUpdateUser() {
        User user4 = new User();
        user4.setId(1L);
        user4.setLogin("u4");
        user4.setName("user4");
        user4.setEmail("user4@email.com");

        userController.update(user4);

        assertEquals(userController.findUserById(1).getName(), "user4");
    }

    @Test
    void shouldDeleteUser() {
        userController.delete(1L);
        assertEquals(userController.findAll().size(), 0);
    }

    @Test
    void shouldNotRemoveFriend() {
        Assertions.assertThrows(NotFoundException.class, () -> userController.removeFriend(1L, 2L));
    }

    void addValidUser() {
        User user2 = new User();
        user2.setLogin("u2");
        user2.setName("user2");
        user2.setEmail("user2@email.com");
        user2.setBirthday("2000-01-01");

        userController.create(user2);
    }

    void addSecondValidUser() {
        User user3 = new User();
        user3.setLogin("u3");
        user3.setName("user3");
        user3.setEmail("user3@email.com");
        user3.setBirthday("2000-01-01");

        userController.create(user3);
    }
}
