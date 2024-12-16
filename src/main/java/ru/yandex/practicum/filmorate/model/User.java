package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.interfaces.BirthdayValidationConstraints;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.LoginValidationConstraints;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

import java.util.HashSet;
import java.util.Set;

/**
 * User.
 */

@Data
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
    private Set<Long> friendsIds = new HashSet<>();
}
