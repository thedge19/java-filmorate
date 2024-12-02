package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.BirthdayValidationConstraints;
import ru.yandex.practicum.filmorate.validator.interfaces.LoginValidationConstraints;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    @NotNull(groups = Created.class)
    @Email(groups = {Created.class, Updated.class})
    private String email;
    @NotNull(groups = Created.class)
    @LoginValidationConstraints(groups = {Created.class, Updated.class})
    private String login;
    private String name;
    @BirthdayValidationConstraints(groups = {Created.class, Updated.class})
    private String birthday;
}
