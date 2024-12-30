package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public Collection<User> findAll() {
        log.info("Вызывается список всех пользователей");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) {
        log.info("Поиск пользолвателя с id={}", id);
        User findedUser = service.findById(id);
        log.info("Пользователь с id={} найден", findedUser.getId());
        return findedUser;
    }

    @PostMapping
    public User create(
            @RequestBody
            @Validated(Created.class)
            User user) {
        log.info("Создаётся пользователь {}", user);
        User createdUser = service.create(user);
        log.info("Пользователь {} создан", createdUser);
        return createdUser;
    }

    @PutMapping
    public User update(
            @RequestBody
            @Validated(Updated.class)
            User newUser) {
        log.info("Обновляется пользователь {}", newUser);
        User updatedUser = service.update(newUser);
        log.info("Пользователь с id={} обновлён", updatedUser);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Удаляется пользователь с id={}", id);
        service.deleteById(id);
        log.info("Пользователь с id={} удалён", id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Пользователь с id={} добавляется в друзья к пользователю с id={}", friendId, id);
        service.addFriend(id, friendId);
        log.info("Пользователи с id={} и id={} теперь друзья", id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable long id) {
        log.info("Получение списка друзей пользователя с id={}", id);
        Set<User> friends = service.getFriends(id);
        log.info("Список друзей пользователя с id={}: {}", id, friends);
        return friends;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("Получение общего списка друзей пользователей с id={} и id={}", id, otherId);
        Set<User> commonFriends = service.getCommonFriends(id, otherId);
        log.info("Список общих друзей пользователей с id={} и {}: {}", id, otherId, commonFriends);
        return commonFriends;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Удаление у пользователя с id={} друга с id={}", id, friendId);
        service.removeFriend(id, friendId);
        log.info("ПОльзователь с id={} удалён из друзей пользователя с id={}", friendId, id);
    }
}
