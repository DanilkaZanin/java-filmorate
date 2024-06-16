package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTests {
    private final UserController userController = new UserController();
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin("user");
        user.setEmail("user@yandex.ru");
        user.setName("user");
        user.setBirthday(LocalDate.of(2001, 1, 1));
    }

    @Test
    public void shouldAddUser() {
        userController.createUser(user);
        assertEquals(List.of(user), userController.getUsers());
    }

    @Test
    public void shouldNotAddUserWithoutSymbolAt() {
        user.setEmail("user.ru");
        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
    public void shouldNotAddUserWithEmptyEmail() {
        user.setEmail("");
        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
    public void shouldNotAddUserWithSpace() {
        user.setLogin("user u");
        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
    public void shouldNotAddUserWithEmptyLogin() {
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
    public void shouldAddUserWithEmptyName() {
        user.setName("");

        userController.createUser(user);
        assertEquals("user", userController.getUsers().getFirst().getName());
    }

    @Test
    public void shouldNotAddUserWithBirthdayInFuture() {
        user.setBirthday(LocalDate.of(3025, 1, 1));
        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }


}
