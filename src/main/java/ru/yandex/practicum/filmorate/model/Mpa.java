package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

@Data
public class Mpa {
    @NotNull(groups = {Created.class, Updated.class})
    @Max(5)
    private long id;
    private String name;
}
