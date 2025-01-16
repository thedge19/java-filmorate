package ru.yandex.practicum.filmorate.storage.interfaces;

import java.util.Collection;

public interface LikesStorage {
    Collection<Long> getLikedUsersIds(long filmId);
}
