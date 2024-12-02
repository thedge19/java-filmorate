package ru.yandex.practicum.filmorate.validator.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validator.validators.BirthdayValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthdayValidator.class)
public @interface BirthdayValidationConstraints {
    String message() default "Дата рождения не может быть в будущем.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
