package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendsStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@Primary
public class FriendsDbStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

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
    public Set<User> getCommonFriends(long userId, long otherId) {
        String q = "SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ? INTERSECT (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?));               ";

        List<User> commonFriends = jdbcTemplate.query(q, userRowMapper, userId, otherId);
        log.info("Common friends: {}", commonFriends);

        return new HashSet<>(jdbcTemplate.queryForList(q, User.class, userId, otherId));
    }

    @Override
    public Collection<Long> getFriends(long userId) {
        String q = "SELECT ID FROM USERS WHERE ID = ?";
        jdbcTemplate.queryForObject(q, Long.class, userId);
        String qFriends = "SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?";
        return jdbcTemplate.queryForList(qFriends, Long.class, userId);
    }


}
