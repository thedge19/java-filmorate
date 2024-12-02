package ru.yandex.practicum.filmorate.validator.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validator.interfaces.BirthdayValidationConstraints;

import java.time.LocalDate;

public class BirthdayValidator implements ConstraintValidator<BirthdayValidationConstraints, String> {

    @Override
    public void initialize(BirthdayValidationConstraints constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String birthday, ConstraintValidatorContext context) {
        if (LocalDate.parse(birthday).isAfter(LocalDate.now())) {
            String customMessage = "Дата рождения не может быть в будущем.";
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}
