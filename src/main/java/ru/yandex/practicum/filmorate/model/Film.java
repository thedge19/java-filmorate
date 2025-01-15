package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.FilmDateValidationConstraints;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Film.
 */

@Data
public class Film {
    private Long id;
    @NotNull(groups = Created.class)
    @NotEmpty(groups = Created.class)
    private String name;
    @Length(max = 200, groups = {Created.class, Updated.class})
    private String description;
    @FilmDateValidationConstraints(groups = {Created.class, Updated.class})
    private String releaseDate;
    @Positive(groups = {Created.class, Updated.class})
    private Integer duration;

    private Set<Long> likedUsersIds = new HashSet<>();
    private Mpa mpa;
    private Set<Genre> genres = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id) && Objects.equals(name, film.name) && Objects.equals(duration, film.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration);
    }
}
