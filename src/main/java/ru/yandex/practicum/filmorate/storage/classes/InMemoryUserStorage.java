package ru.yandex.practicum.filmorate.storage.classes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        log.info("Finding all users: {}", users.values());
        return users.values();
    }

    @Override
    public User findById(long id) {
        if (users.get(id) == null) {
            log.error("User {} not found", id);
            throw new NotFoundException("Пользователь не найден findById");
        }
        return users.get(id);
    }

    @Override
    public User create(User user) {
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

    @Override
    public User update(User newUser) {
        // проверяем необходимые условия
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

    @Override
    public void deleteById(long id) {
        users.remove(id);
        log.info("Пользователь с id {}", id + "удалён");
    }

    @Override
    public void addFriend(long userId, long friendId) {
        User user = users.get(userId);
        User addedFriend = users.get(friendId);

        user.getFriendsIds().add(addedFriend.getId());
        addedFriend.getFriendsIds().add(user.getId());

        log.info("Подьзователь с id={} и пользователь с id={} теперь друзья", users.get(userId).getId(), users.get(friendId).getId());
    }

    @Override
    public Set<Long> getFriends(long userId) {
        if (users.get(userId) == null) {
            log.error("Пользователь с id={} не найден", userId);
            throw new NotFoundException("Пользователь не найден");
        }
        return users.get(userId).getFriendsIds();
    }

    @Override
    public Set<Long> getCommonFriends(long userId, long otherId) {
        Set<Long> userFriendsIds = users.get(userId).getFriendsIds();
        Set<Long> otherFriendsIds = users.get(otherId).getFriendsIds();
        userFriendsIds.retainAll(otherFriendsIds);
        log.info("Общий список друзей пользователя с id={} и id={}: {}", userId, otherId, userFriendsIds);
        return userFriendsIds;
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        if (users.get(userId).getFriendsIds().contains(friendId)) {
            log.info("Пользователи с id={} и {} больше не друзья", userId, friendId);
            users.get(userId).getFriendsIds().remove(friendId);
            users.get(friendId).getFriendsIds().remove(userId);
        }
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
