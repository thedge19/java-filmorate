package ru.yandex.practicum.filmorate.dal.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.interfaces.LikesStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class FilmRowMapper implements RowMapper<Film> {

    private final MpaStorage mpaStorage;
    private final LikesStorage likesStorage;
    private final GenreService genreService;

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
        film.getGenres().addAll(genreService.getFilmGenres(film.getId()));

        return film;
    }
}
