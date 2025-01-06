package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("USER_ID"));
        user.setEmail(rs.getString("EMAIL"));
        user.setLogin(rs.getString("LOGIN"));
        user.setName(rs.getString("NAME"));
        user.setBirthday(String.valueOf(rs.getDate("BIRTHDAY").toLocalDate()));

//        user.getFriendsIds().addAll(friendsStorage.getFriendsIds(user.getId()));

        return user;
    }
}
