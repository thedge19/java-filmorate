package ru.yandex.practicum.filmorate.storage.interfaces;

import java.util.Collection;

public interface FriendsStorage {
    void addFriend(long userId, long friendId);

    void removeFriend(long userId, long friendId);

    Collection<Long> getFriends(long userId);
}
