package ru.yandex.practicum.filmorate.services;

import java.time.LocalDate;
import java.util.Map;

public class Service {

    public long getNextId(Map<Long, ?> elements) {
        long currentMaxId = elements.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public boolean checkDate(String dateForComparison, LocalDate dateToCompareItWith) {
        LocalDate dayOfRelease = LocalDate.parse(dateForComparison);
        return dayOfRelease.isAfter(dateToCompareItWith);
    }
}
