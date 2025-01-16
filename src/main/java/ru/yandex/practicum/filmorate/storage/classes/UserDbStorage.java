package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper mapper;

    @Override
    public Collection<User> findAll() {
        String q = "SELECT ID, EMAIL, LOGIN, NAME, BIRTHDAY FROM USERS";
        return jdbcTemplate.query(q, mapper);
    }

    @Override
    public Set<String> findEmails() {
        return Set.of();
    }

    @Override
    public User findById(long id) {
        String q = "SELECT ID, EMAIL, LOGIN, NAME, BIRTHDAY" +
                " FROM USERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(q, mapper, id);
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("EMAIL", user.getEmail());
        parameters.put("LOGIN", user.getLogin());
        parameters.put("NAME", user.getName());
        parameters.put("BIRTHDAY", user.getBirthday());

        Number userId = jdbcInsert.executeAndReturnKey(parameters);

        user.setId(userId.longValue());

        return user;
    }

    @Override
    public User update(User newUser) {
        jdbcTemplate.queryForObject("SELECT ID FROM USERS WHERE ID = ?", Long.class, newUser.getId());
        String updateQuery = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE ID = ?";

        jdbcTemplate.update(updateQuery,
                newUser.getEmail(),
                newUser.getLogin(),
                newUser.getName(),
                newUser.getBirthday(),
                newUser.getId());

        return newUser;
    }

    @Override
    public void deleteById(long id) {
        String q = "DELETE FROM USERS WHERE ID = ?";
        jdbcTemplate.update(q, id);
    }

    @Override
    public Set<String> getEmails() {
        return Set.of();
    }

    @Override
    public boolean userNotExists(long id) {
        String q = "SELECT CASE WHEN EXISTS (SELECT * FROM USERS WHERE ID = ?) THEN 'TRUE' ELSE 'FALSE' END";
        return !Boolean.TRUE.equals(jdbcTemplate.queryForObject(q, Boolean.class, id));
    }
}
