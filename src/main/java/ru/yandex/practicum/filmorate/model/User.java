package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validator.interfaces.AdvanceInfo;
import ru.yandex.practicum.filmorate.validator.interfaces.BasicInfo;
import ru.yandex.practicum.filmorate.validator.interfaces.BirthdayValidationConstraints;
import ru.yandex.practicum.filmorate.validator.interfaces.LoginValidationConstraints;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    @NotNull(groups = BasicInfo.class)
    @Email(groups = {BasicInfo.class, AdvanceInfo.class})
    private String email;
    @NotNull(groups = BasicInfo.class)
    @LoginValidationConstraints(groups = {BasicInfo.class, AdvanceInfo.class})
    private String login;
    private String name;
    @BirthdayValidationConstraints(groups = {BasicInfo.class, AdvanceInfo.class})
    private String birthday;
}
