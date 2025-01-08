package ru.yandex.practicum.filmorate.dal.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikesStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmRowMapper implements RowMapper<Film> {

    private final MpaStorage mpaStorage;
    private final LikesStorage likesStorage;
    private final GenreStorage genreStorage;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("ID"));
        film.setName(rs.getString("NAME"));
        film.setDescription(rs.getString("DESCRIPTION"));
        film.setReleaseDate(String.valueOf(rs.getDate("RELEASE_DATE").toLocalDate()));
        film.setDuration(rs.getInt("DURATION"));
        film.setMpa(mpaStorage.get(rs.getLong("MPA_ID")));
        film.getLikedUsersIds().addAll(likesStorage.getLikedUsersIds(film.getId()));

        String genresQuery = "SELECT GENRE_ID FROM FILMS_GENRES WHERE FILM_ID = ?";
        List<Long> genresIds = jdbcTemplate.queryForList(genresQuery, Long.class, film.getId());
        Collections.sort(genresIds);
        for (Long genreId : genresIds) {
            film.getGenres().add(genreStorage.get(genreId));
        }

        return film;
    }
}
