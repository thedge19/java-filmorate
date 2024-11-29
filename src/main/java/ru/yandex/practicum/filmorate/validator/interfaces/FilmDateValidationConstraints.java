package ru.yandex.practicum.filmorate.validator.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validator.validators.FilmDateValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FilmDateValidator.class)
public @interface FilmDateValidationConstraints {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
