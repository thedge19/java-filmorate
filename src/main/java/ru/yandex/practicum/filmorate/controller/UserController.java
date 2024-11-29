package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody @Validated User user) {
        // проверяем выполнение необходимых условий
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        // формируем дополнительные данные
        user.setId(getNextId(users));
        // сохраняем новую публикацию в памяти приложения
        users.put(user.getId(), user);
        log.info("Пользователь с id {} добавлен", user.getId());
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Validated User newUser) {
//      проверяем необходимые условия
        if (users.get(newUser.getId()) != null) {
            User oldUser = users.get(newUser.getId());
            if (newUser.getEmail() != null) {
                oldUser.setEmail(newUser.getEmail());
            }
            if (newUser.getLogin() != null) {
                oldUser.setLogin(newUser.getLogin());
            }
            if (newUser.getName() != null) {
                oldUser.setName(newUser.getName());
            }
            if (newUser.getBirthday() != null) {
                oldUser.setBirthday(newUser.getBirthday());
            }
            // если публикация найдена и все условия соблюдены, обновляем её содержимое
            users.put(oldUser.getId(), oldUser);
            log.info("Пользователь с id {} обновлён", oldUser.getId());
            return oldUser;
        }
        log.error("Пользователь с id = {} не найден", newUser.getId());
        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    private long getNextId(Map<Long, ?> elements) {
        long currentMaxId = elements.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
