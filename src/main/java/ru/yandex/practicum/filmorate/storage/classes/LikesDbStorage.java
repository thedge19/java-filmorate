package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.interfaces.LikesStorage;

import java.util.Collection;

@Component
@Primary
@RequiredArgsConstructor
public class LikesDbStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Long> getLikedUsersIds(long filmId) {
        String q = "SELECT USER_ID FROM LIKES WHERE FILM_ID = ?";
        return jdbcTemplate.queryForList(q, Long.class, filmId);
    }
}
