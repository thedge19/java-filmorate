package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validator.interfaces.FilmDateValidationConstraints;

/**
 * Film.
 */

@Data
@AllArgsConstructor
public class Film {
    private Long id;
    @NotNull(message = "Название не может быть пустым")
    @NotEmpty(message = "Название не может быть пустым")
    private String name;
    @Length(max = 200, message = "Максимальная длина описания — 200 символов.")
    private String description;
    @FilmDateValidationConstraints
    private String releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Integer duration;
}
