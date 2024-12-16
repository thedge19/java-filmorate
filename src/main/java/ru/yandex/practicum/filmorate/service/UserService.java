package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User findById(long id) {
        return userStorage.findById(id);
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User newUser) {
        if (findById(newUser.getId()) != null) {
            return userStorage.update(newUser);
        }
        log.error("Пользователь с id = {} не найден update", newUser.getId());
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    public void addFriend(long userId, long friendId) {
        if (userStorage.findById(userId) == null || userStorage.findById(friendId) == null) {
            log.error("Пользователь с id={} или {} не найден addFriend", userId, friendId);
            throw new NotFoundException("Пользователь не найден");
        }
        userStorage.addFriend(userId, friendId);
    }

    public Set<User> getFriends(long userId) {
        Set<Long> friendIds = userStorage.getFriends(userId);
        Set<User> friends = new HashSet<>();
        for (Long id : friendIds) {
            friends.add(userStorage.findById(id));
        }
        return friends;
    }

    public Set<User> getCommonFriends(long userId, long otherId) {
        if (userStorage.findById(userId) == null || userStorage.findById(otherId) == null) {
            log.error("Пользователь с id={} или {} не найден getCommonFriends", userId, otherId);
            throw new NotFoundException("Пользователь не найден");
        }
        Set<Long> commonFriendsIds = userStorage.getCommonFriends(userId, otherId);
        Set<User> commonFriends = new HashSet<>();
        for (Long id : commonFriendsIds) {
            commonFriends.add(userStorage.findById(id));
        }
        return commonFriends;
    }

    public void removeFriend(long userId, long friendId) {
        if (userStorage.findById(userId) == null || userStorage.findById(friendId) == null) {
            log.error("Ошибка удаления пользователя с id={} иp друзей пользователя с id={}", friendId, userId);
            throw new NotFoundException("Пользователь не найден");
        }
        userStorage.removeFriend(userId, friendId);
    }

    public void deleteById(long id) {
        userStorage.deleteById(id);
        log.info("Пользователь с id {}", id + "удалён");
    }
}
