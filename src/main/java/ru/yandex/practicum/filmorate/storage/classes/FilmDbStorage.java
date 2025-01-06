package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmRowMapper mapper;
    private final MpaStorage mpaStorage;

    @Override
    public Collection<Film> findAll() {
        String q = "SELECT ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID FROM FILMS";
        return jdbcTemplate.query(q, mapper);
    }

    @Override
    public Film findById(long id) {
        String q = "SELECT ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID FROM FILMS WHERE ID = ?";
        return jdbcTemplate.queryForObject(q, mapper, id);
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("ID");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", film.getName());
        parameters.put("description", film.getDescription());
        parameters.put("release_date", film.getReleaseDate());
        parameters.put("duration", film.getDuration());
        parameters.put("MPA_ID", film.getMpa().getId());

        Number filmId = jdbcInsert.executeAndReturnKey(parameters);
        film.setId(filmId.longValue());

        return film;
    }

    @Override
    public Film update(Film newFilm) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void like(Film likedFilm, long userId) {

    }

    @Override
    public void unlike(Film unlikedFilm, long userId) {

    }
}
