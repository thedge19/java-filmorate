package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.Collection;

@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreRowMapper mapper;

    @Override
    public Collection<Genre> findAll() {
        String q = "SELECT ID, NAME FROM GENRES";
        return jdbcTemplate.query(q, mapper);
    }

    @Override
    public Genre get(long id) {
        String q = "SELECT ID, NAME FROM GENRES WHERE ID = ?";
        return jdbcTemplate.queryForObject(q, new GenreRowMapper(), id);
    }
}
