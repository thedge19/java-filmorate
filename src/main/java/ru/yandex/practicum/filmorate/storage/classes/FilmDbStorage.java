package ru.yandex.practicum.filmorate.storage.classes;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.*;

@Component
@RequiredArgsConstructor
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmRowMapper mapper;
    private final GenreService genreService;

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

        updateGenresIntermediaryTable(film);

        return film;
    }

    @Override
    public Film update(Film newFilm) {
        String updateQuery = "UPDATE FILMS SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_ID = ? WHERE ID = ?";
        jdbcTemplate.update(updateQuery,
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getReleaseDate(),
                newFilm.getDuration(),
                newFilm.getMpa().getId(),
                newFilm.getId());
        newFilm.getGenres().addAll(genreService.getFilmGenres(newFilm.getId()));

        updateGenresIntermediaryTable(newFilm);

        return newFilm;
    }

    @Override
    public void deleteById(long id) {
        String q = "DELETE FROM FILMS WHERE ID = ?";
        jdbcTemplate.update(q, id);
    }

    @Override
    public void like(Film likedFilm, long userId) {
        String q = "INSERT INTO LIKES (USER_ID, FILM_ID) VALUES (?, ?)";
        jdbcTemplate.update(q, userId, likedFilm.getId());
    }

    @Override
    public void unlike(Film unlikedFilm, long userId) {
        String q = "DELETE FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(q, userId, unlikedFilm.getId());
    }

    @Override
    public Collection<Film> popularFilms(Integer count) {

        String q = "SELECT ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID FROM FILMS AS F " +
                "LEFT OUTER JOIN LIKES AS L ON F.ID = L.FILM_ID " +
                "GROUP BY F.ID " +
                "ORDER BY COUNT(L.FILM_ID) " +
                "DESC " +
                "LIMIT " + count;

        return jdbcTemplate.query(q, mapper);
    }

    private void updateGenresIntermediaryTable(Film film) {
        String deleteGenresQuery = "DELETE FROM FILMS_GENRES WHERE FILM_ID = ?";
        jdbcTemplate.update(deleteGenresQuery, film.getId());

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            StringBuilder insertGenresQuery = new StringBuilder("INSERT INTO FILMS_GENRES (FILM_ID, GENRE_ID) VALUES ");
            List<Object> parameters = new ArrayList<>();

            List<Genre> genres = new ArrayList<>(film.getGenres());

            for (int i = 0; i < film.getGenres().size(); i++) {
                insertGenresQuery.append("(?, ?)");
                if (i < film.getGenres().size() - 1) {
                    insertGenresQuery.append(", ");
                }
                parameters.add(film.getId());
                parameters.add(genres.get(i).getId());
            }

            jdbcTemplate.update(insertGenresQuery.toString(), parameters.toArray());
        }
    }

}
