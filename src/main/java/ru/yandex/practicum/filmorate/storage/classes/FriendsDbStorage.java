package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendsStorage;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@Primary
public class FriendsDbStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(long userId, long friendId) {
        String q = "INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES (?, ?)";
        jdbcTemplate.update(q, userId, friendId);
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        String q = "DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(q, userId, friendId);
    }

    @Override
    public Collection<Long> getFriends(long userId) {
        String q = "SELECT ID FROM USERS WHERE ID = ?";
        jdbcTemplate.queryForObject(q, Long.class, userId);
        String qFriends = "SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?";
        return jdbcTemplate.queryForList(qFriends, Long.class, userId);
    }
}
