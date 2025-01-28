package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface FriendsStorage {
    void addFriend(long userId, long friendId);

    void removeFriend(long userId, long friendId);

    Set<User> getCommonFriends(long userId, long otherId);

    Collection<Long> getFriends(long userId);
}
