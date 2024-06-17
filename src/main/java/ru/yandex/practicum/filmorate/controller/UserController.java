package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

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
    public User createUser(@Valid @RequestBody final User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(getNextId());
        users.put(user.getId(), user);

        log.info("User created: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody final User user) {
        if (users.containsKey(user.getId())) {
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                user.setName(user.getLogin());
            }

            users.put(user.getId(), user);
            log.info("User updated: {}", user.getId());
            return user;
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

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
