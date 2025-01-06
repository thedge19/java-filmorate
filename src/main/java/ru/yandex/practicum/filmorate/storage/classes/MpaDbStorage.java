package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.util.Collection;

@Component
@Primary
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaRowMapper mapper;

    @Override
    public Collection<Mpa> getAll() {
        String q = "SELECT ID, NAME FROM MPA";
        return jdbcTemplate.query(q, mapper);
    }

    @Override
    public Mpa get(long id) {
        String q = "SELECT ID,NAME FROM MPA WHERE ID = ?";
        return jdbcTemplate.queryForObject(q, new MpaRowMapper(), id);
    }
}
