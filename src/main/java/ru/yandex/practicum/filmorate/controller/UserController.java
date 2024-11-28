package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.logs.LogMessage;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private final Service service = new Service();
    private final static Logger log = LoggerFactory.getLogger(User.class);
    private final LogMessage lMsg = new LogMessage();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        // проверяем выполнение необходимых условий
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error("Invalid email address: {}", user.getEmail());
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.error("Invalid login: {}", user.getLogin());
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (service.checkDate(user.getBirthday(), LocalDate.now())) {
            log.error("Invalid birthday: {}", user.getBirthday());
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        // формируем дополнительные данные
        user.setId(service.getNextId(users));
        // сохраняем новую публикацию в памяти приложения
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
//      проверяем необходимые условия
        if (users.get(newUser.getId()) != null) {
            User oldUser = users.get(newUser.getId());
            if (newUser.getEmail() != null) {
                if (!newUser.getEmail().contains("@")) {
                    throw new ValidationException("Электронная почта должна содержать символ");
                }
                oldUser.setEmail(newUser.getEmail());
            }
            if (newUser.getLogin() != null) {
                if (newUser.getLogin().contains(" ")) {
                    throw new ValidationException("Логин не может содержать пробелы");
                }
                oldUser.setLogin(newUser.getLogin());
            }
            if (newUser.getName() != null) {
                oldUser.setName(newUser.getName());
            }
            if (newUser.getBirthday() != null) {
                if (service.checkDate(newUser.getBirthday(), LocalDate.now())) {
                    throw new ValidationException("Дата рождения не может быть в будущем");
                }
                oldUser.setBirthday(newUser.getBirthday());
            }
            // если публикация найдена и все условия соблюдены, обновляем её содержимое
            users.put(oldUser.getId(), oldUser);
            return oldUser;
        }
        log.error(lMsg.getUndiscoveredId("Пользователь"), newUser.getId());
        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден");
    }
}
