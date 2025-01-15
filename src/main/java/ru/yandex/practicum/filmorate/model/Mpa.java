package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.interfaces.Created;
import ru.yandex.practicum.filmorate.validator.interfaces.Updated;

import java.util.Objects;

@Data
public class Mpa {
    @NotNull(groups = {Created.class, Updated.class})
    @Max(5)
    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mpa mpa = (Mpa) o;
        return id == mpa.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
