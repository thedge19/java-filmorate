package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.InternalErrorException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
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
        verifyingTheUsersExistence(userId);
        verifyingTheUsersExistence(friendId);
        userStorage.addFriend(userId, friendId);
    }

    public Set<User> getFriends(long userId) {
        verifyingTheUsersExistence(userId);
        Set<Long> friendIds = userStorage.getFriends(userId);
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
        User user = verifyingTheUsersExistence(userId);
        User friend = verifyingTheUsersExistence(friendId);

        if (user.getFriendsIds().contains(friend.getId())) {
            userStorage.removeFriend(userId, friendId);
        }

        userStorage.removeFriend(userId, friendId);
    }

    public void deleteById(long id) {
        verifyingTheUsersExistence(id);
        userStorage.deleteById(id);
    }

    private User verifyingTheUsersExistence(long id) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id= " + id + " не найден");
        }
        return user;
    }

    private void updateEmailInSet(String oldEmail, String newEmail) {
        userStorage.getEmails().remove(oldEmail);
        userStorage.getEmails().add(newEmail);
    }
}
