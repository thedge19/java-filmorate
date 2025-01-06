package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.*;

@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    public Set<String> findEmails() {
        return emails;
    }

    @Override
    public User findById(long id) {
        return users.get(id);
    }

    @Override
    public User create(User user) {
        // формируем дополнительные данные
        user.setId(getNextId(users));
        // сохраняем новую публикацию в памяти приложения
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        return user;
    }

    @Override
    public User update(User newUser) {
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public void deleteById(long id) {
        emails.remove(users.get(id).getEmail());
        users.remove(id);
    }

    @Override
    public Set<String> getEmails() {
        return emails;
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
