package ru.yandex.practicum.filmorate.dal.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class FilmRowMapper implements RowMapper<Film> {

    private final MpaStorage mpaStorage;

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("ID"));
        film.setName(rs.getString("NAME"));
        film.setDescription(rs.getString("DESCRIPTION"));
        film.setReleaseDate(String.valueOf(rs.getDate("RELEASE_DATE").toLocalDate()));
        film.setDuration(rs.getInt("DURATION"));
        film.setMpa(mpaStorage.get(rs.getLong("ID")));

//        String genresQuery = "SELECT GENRE_ID FROM FILMS_GENRES WHERE FILM_ID = ?";
//        List<Long> genresIds = jdbcTemplate.queryForList(genresQuery, Long.class, film.getId());
//        for (Long genreId : genresIds) {
//            film.getGenres().add(genreStorage.get(genreId));

        return film;
    }
}
