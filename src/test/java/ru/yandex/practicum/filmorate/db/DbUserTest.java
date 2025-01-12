package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.classes.UserDbStorage;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(scripts = {"/schema.sql", "/testUserData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DbUserTest {
    private final UserDbStorage userStorage;

    @Test
    void shouldGetAll() {
        assertThat(userStorage.findAll()).hasSize(2);
    }

    @Test
    void shouldGetUser() {
        assertThat(userStorage.findById(1))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("email", "KevinNash@wolfpack.com")
                .hasFieldOrPropertyWithValue("login", "KevinNash")
                .hasFieldOrPropertyWithValue("name", "Kevin")
                .hasFieldOrPropertyWithValue("birthday", "1959-07-09");
    }

    @Test
    void shouldCreateUser() {
        assertThat(userStorage.findAll()).hasSize(2);

        User newUser = new User();
        newUser.setEmail("BretHart@wolfpack.com");
        newUser.setLogin("BretHart");
        newUser.setName("Bret");
        newUser.setBirthday("1957-07-02");

        userStorage.create(newUser);

        assertThat(userStorage.findAll()).hasSize(3);
    }

    @Test
    void delete() {
        assertThat(userStorage.findAll()).hasSize(2);
        userStorage.deleteById(1);
        assertThat(userStorage.findAll()).hasSize(1);
    }
}
