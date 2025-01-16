package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaStorage {
    Collection<Mpa> findAll();

    Mpa get(long id);

    Mpa findNameById(long id);

    boolean mpaExists(long id);
}
