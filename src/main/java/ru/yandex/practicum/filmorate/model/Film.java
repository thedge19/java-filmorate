package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validator.interfaces.AdvanceInfo;
import ru.yandex.practicum.filmorate.validator.interfaces.BasicInfo;
import ru.yandex.practicum.filmorate.validator.interfaces.FilmDateValidationConstraints;

/**
 * Film.
 */

@Data
@AllArgsConstructor
public class Film {
    private Long id;
    @NotNull(groups = BasicInfo.class)
    @NotEmpty(groups = BasicInfo.class)
    private String name;
    @Length(max = 200, groups = {BasicInfo.class, AdvanceInfo.class})
    private String description;
    @FilmDateValidationConstraints(groups = {BasicInfo.class, AdvanceInfo.class})
    private String releaseDate;
    @Positive(groups = {BasicInfo.class, AdvanceInfo.class})
    private Integer duration;
}
