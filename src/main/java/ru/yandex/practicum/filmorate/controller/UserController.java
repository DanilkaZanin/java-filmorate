package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Long, User> users = new HashMap<>();

    @PostMapping
    public User createUser(@RequestBody final User user) {
        user.setId(getNextId());
        if (checkEmail(user.getEmail()) && checkLogin(user.getLogin()) && checkBirthday(user.getBirthday())) {
            if (user.getName().trim().isEmpty()) {
                user.setName(user.getLogin());
            }

            users.put(user.getId(), user);

            log.info("User created: {}", user);
            return user;
        } else {
            log.info("User creation failed: {}", user);
            throw new ValidationException("Проверьте корректность полей!");
        }
    }

    @PutMapping
    public User updateUser(@RequestBody final User user) {
        if (users.containsKey(user.getId())) {
            if (checkEmail(user.getEmail()) && checkLogin(user.getLogin()) && checkBirthday(user.getBirthday())) {
                if (user.getName().trim().isEmpty()) {
                    user.setName(user.getLogin());
                }

                users.put(user.getId(), user);

                log.info("User updated: {}", user.getId());
                return user;
            } else {
                log.info("User update failed with ValidationException: {}", user);
                throw new ValidationException("Проверьте корректность полей!");
            }
        } else {
            log.info("User update failed with noSuchElementException: {}", user);
            throw new NoSuchElementException("Такого пользователя нет!");
        }
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Users list was got");
        return new ArrayList<>(users.values());
    }

    /**
     * Проверка на наличие символа @ в мэйле
     *
     * @return true когда символ @ содержится, false когда символа @ нет
     */
    private boolean checkEmail(String email) {
        return email.contains("@");
    }

    /**
     * Проверка на наличие пробела в логине
     *
     * @return true если пробелов нет, false если он есть
     */
    private boolean checkLogin(String login) {
        return !login.isEmpty() && !login.contains(" ");
    }

    /**
     * Проверка на корректность даты рождения
     *
     * @return true если дата рождения была в прошлом, false если дата в будущем
     */
    private boolean checkBirthday(LocalDate birthday) {
        return birthday.isBefore(LocalDate.now());
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
