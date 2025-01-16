package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.classes.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.classes.UserDbStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(scripts = {"/schema.sql", "/testFilmData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DbFilmTest {
    private final FilmDbStorage filmStorage;
    private final UserDbStorage userStorage;

    @Test
    void shouldGetAll() {
        assertThat(filmStorage.findAll()).hasSize(2);
    }

    @Test
    void shouldGetFilm() {
        assertThat(filmStorage.findById(1))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Prestige")
                .hasFieldOrPropertyWithValue("description", "Фильм Нолана с двумя Бэйлами и кучей Хью Джекманов")
                .hasFieldOrPropertyWithValue("releaseDate", "2006-10-20")
                .hasFieldOrPropertyWithValue("duration", 125);
    }

    @Test
    void shouldCreateFilm() {
        assertThat(filmStorage.findAll()).hasSize(2);
        createFilm();
        assertThat(filmStorage.findAll()).hasSize(3);
    }

    @Test
    void delete() {
        assertThat(filmStorage.findAll()).hasSize(2);
        filmStorage.deleteById(1);
        assertThat(filmStorage.findAll()).hasSize(1);
    }

    @Test
    void shouldGetPopularFilms() {
        User user1 = userStorage.findById(1);
        User user2 = userStorage.findById(2);
        createFilm();
        Film film1 = filmStorage.findById(1);
        Film film2 = filmStorage.findById(2);

        filmStorage.like(film2, user1.getId());
        filmStorage.like(film1, user2.getId());
        filmStorage.like(film2, user2.getId());

        List<Film> films = new ArrayList<>(filmStorage.popularFilms(3));
        assert (Objects.equals(films.getFirst().getName(), "Terminator: Dark Fate"));
    }

    void createFilm() {
        Mpa mpa1 = new Mpa();
        mpa1.setId(1);
        mpa1.setName("Mpa 1");

        Film newFilm = new Film();
        newFilm.setName("The Old Man and the Lisa");
        newFilm.setDescription("Борьба Лизы Симпсон и мистера Бёрнса");
        newFilm.setReleaseDate("1997-04-20");
        newFilm.setDuration(30);
        newFilm.setMpa(mpa1);

        filmStorage.create(newFilm);
    }
}
