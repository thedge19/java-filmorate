package ru.yandex.practicum.filmorate.validator.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validator.interfaces.FilmDateValidationConstraints;

import java.time.LocalDate;

public class FilmDateValidator implements ConstraintValidator<FilmDateValidationConstraints, String> {
    @Override
    public void initialize(FilmDateValidationConstraints constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String releaseDateOfTheFilm, ConstraintValidatorContext context) {
        if (!LocalDate.parse(releaseDateOfTheFilm).isAfter(LocalDate.parse("1895-12-27"))) {
            String customMessage = "Дата релиза должна быть не раньше 28 декабря 1895 года";
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}
