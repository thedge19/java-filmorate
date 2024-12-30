package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.model.enums.Rating;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.FilmDateValidationConstraints;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private Rating rating;
    private List<Long> genreIds = new ArrayList<>();
}
