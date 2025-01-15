package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.Objects;

/**
 * Genre.
 */

@Data
public class Genre {
    private Long id;
    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre genre)) return false;

        return getId().equals(genre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
