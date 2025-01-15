package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    Collection<User> findAll();

    Set<String> findEmails();

    User findById(long id);

    User create(User user);

    User update(User newUser);

    void deleteById(long id);

    Set<String> getEmails();

    boolean userExists(long id);
}
