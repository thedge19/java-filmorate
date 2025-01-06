package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InternalErrorException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User findById(long id) {
        verifyingTheUsersExistence(id);
        return userStorage.findById(id);
    }

    public User create(User user) {
        String email = user.getEmail();
        // проверяем выполнение необходимых условий
        if (userStorage.findEmails().contains(email)) {
            throw new InternalErrorException("Пользователь с email " + email + " уже существует");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User update(User newUser) {

        User oldUser = verifyingTheUsersExistence(newUser.getId());
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
        if (!Objects.equals(oldUser.getEmail(), newUser.getEmail())) {
            updateEmailInSet(oldUser.getEmail(), newUser.getEmail());
        }
        // если публикация найдена и все условия соблюдены, обновляем её содержимое
        return userStorage.update(oldUser);
    }

    public void addFriend(long userId, long friendId) {
        if ((findAll().stream().noneMatch(user -> user.getId() == userId))
                || (findAll().stream().noneMatch(user -> user.getId() == friendId))) {
            log.warn("Ошибка при добавлении пользователей в друзья - пользователь с ID {} или {} не найден", userId, friendId);
            throw new NotFoundException("Не найден пользователь с ID " + userId + " или" + friendId);
        }

        friendsStorage.addFriend(userId, friendId);
    }

    public Set<User> getFriends(long userId) {
        verifyingTheUsersExistence(userId);
        Collection<Long> friendIds = friendsStorage.getFriends(userId);
        Set<User> friends = new HashSet<>();
        for (Long id : friendIds) {
            friends.add(userStorage.findById(id));
        }
        return friends;
    }

    public Set<User> getCommonFriends(long userId, long otherId) {

        User user = verifyingTheUsersExistence(userId);
        User other = verifyingTheUsersExistence(otherId);

        Set<Long> userFriendsIds = user.getFriendsIds();
        Set<Long> otherFriendsIds = other.getFriendsIds();

        userFriendsIds.retainAll(otherFriendsIds);

        Set<User> commonFriends = new HashSet<>();
        for (Long id : userFriendsIds) {
            commonFriends.add(userStorage.findById(id));
        }
        return commonFriends;
    }

    public void removeFriend(long userId, long friendId) {
        userId = verifyingTheUsersExistence(userId).getId();
        friendId = verifyingTheUsersExistence(friendId).getId();

        Collection<Long> friendIds = friendsStorage.getFriends(userId);

        if (!friendIds.contains(friendId)) {
            log.warn("Пользователя с данным id={} нет в друзьях у пользователя с id={}", friendId, userId);
            throw new InternalErrorException("Пользователя с данным id нет в друзьях");
        }

        friendsStorage.removeFriend(userId, friendId);
    }

    public void deleteById(long id) {
        verifyingTheUsersExistence(id);
        userStorage.deleteById(id);
    }

    private User verifyingTheUsersExistence(long id) {

        if (findAll().stream().noneMatch(user -> user.getId() == id)) {
            log.warn("Ошибка - пользователь с ID {} не найден", id);
            throw new NotFoundException("Не найден пользователь с ID " + id);
        }
        return userStorage.findById(id);
    }

    private void updateEmailInSet(String oldEmail, String newEmail) {
        userStorage.getEmails().remove(oldEmail);
        userStorage.getEmails().add(newEmail);
    }
}
