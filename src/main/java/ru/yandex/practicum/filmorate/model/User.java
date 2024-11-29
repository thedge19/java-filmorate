package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.interfaces.BirthdayValidationConstraints;
import ru.yandex.practicum.filmorate.validator.interfaces.LoginValidationConstraints;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    @NotNull(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна содержать символ @")
    private String email;
    @NotNull(message = "Логин не может быть пустым")
    @LoginValidationConstraints(message = "Логин не может содержать пробелы")
    private String login;
    private String name;
    @BirthdayValidationConstraints
    private String birthday;
}
