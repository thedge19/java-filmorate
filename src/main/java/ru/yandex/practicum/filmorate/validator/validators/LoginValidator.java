package ru.yandex.practicum.filmorate.validator.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validator.interfaces.LoginValidationConstraints;

public class LoginValidator implements ConstraintValidator<LoginValidationConstraints, String> {

    @Override
    public void initialize(LoginValidationConstraints constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        if (login.contains(" ")) {
            String customMessage = "Логин не может быть пустым или содержать пробелы";
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}
