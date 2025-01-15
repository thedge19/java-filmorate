package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    public Genre findById(long id) {
        String q = "SELECT ID, NAME FROM GENRES WHERE ID = ?";
        return jdbcTemplate.queryForObject(q, new GenreRowMapper(), id);
    }

    @Override
    public boolean genreExists(long id) {
        String q = "SELECT CASE WHEN EXISTS (SELECT * FROM GENRES WHERE ID = ?) THEN 'TRUE' ELSE 'FALSE' END";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(q, Boolean.class, id));
    }

    @Override
    public List<Long> genreIds(long id) {
        String genresIdQuery = "SELECT GENRE_ID FROM FILMS_GENRES WHERE FILM_ID = ?";
        List<Long> genresIds = jdbcTemplate.queryForList(genresIdQuery, Long.class, id);
        Collections.sort(genresIds);
        return genresIds;
    }

    @Override
    public List<Genre> getFilmGenres(List<Long> genreIds) {
        StringBuilder genresQuery = new StringBuilder("SELECT * FROM GENRES WHERE ID IN (");

        for (int i = 0; i < genreIds.size(); i++) {
            genresQuery.append(genreIds.get(i));
            if (i < genreIds.size() - 1) {
                genresQuery.append(", ");
            } else {
                genresQuery.append(")");
            }
        }

        return jdbcTemplate.query(genresQuery.toString(), new GenreRowMapper());
    }
}
