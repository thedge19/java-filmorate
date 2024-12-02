package ru.yandex.practicum.filmorate.validator.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validator.validators.LoginValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginValidator.class)
public @interface LoginValidationConstraints {
    String message() default "Логин не может быть пустым или содержать пробелы";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
