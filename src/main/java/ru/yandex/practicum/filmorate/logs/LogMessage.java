package ru.yandex.practicum.filmorate.logs;

public class LogMessage {

    public String getFilmDescription() {
        return "Максимальная длина данного описания — {} символов. А должно быть не более 200";
    }

    public String getFilmRelease() {
        return "Дата релиза данного фильма - {}. Это раньше, чем 28 декабря 1895 года";
    }

    public String getPositiveDuration() {
        return "Продолжительность данного фильма - {}. Так не бывает";
    }

    public String getUndiscoveredId(String element) {
        return element + " с id = {} не найден";
    }

    public String getInvalidEmail() {
        return "Электронная почта не может быть пустой и должна содержать символ @, как, например, эта - {}";
    }

    public String getInvalidLogin() {
        return "Логин не может быть пустым или содержать пробелы, как, например, этот - {}";
    }

    public String getInvalidBirthday() {
        return "Дата рождения не может быть в будущем. А эта - {}";
    }
}
