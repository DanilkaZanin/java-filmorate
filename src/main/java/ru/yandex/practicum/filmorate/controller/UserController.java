package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

// пока не понятно как приложение должно реагировать на пустые значения!
@RestController
@RequestMapping("/users")
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();

    /* электронная почта не может быть пустой и должна содержать символ @;
        логин не может быть пустым и содержать пробелы;
        имя для отображения может быть пустым — в таком случае будет использован логин;
        дата рождения не может быть в будущем.
        */

    @PostMapping
    public User createUser(@RequestBody final User user) {
        if(checkEmail(user.getEmail()) && checkLogin(user.getLogin()) && checkBirthday(user.getBirthday())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new ValidationException("Проверьте корректность полей!");
        }
    }

    @PutMapping
    public User updateUser(@RequestBody final User user) {
        if(users.containsKey(user.getId())) {
            if(checkEmail(user.getEmail()) && checkLogin(user.getLogin()) && checkBirthday(user.getBirthday())) {
                users.put(user.getId(), user);
                return user;
            } else {
                throw new ValidationException("Проверьте корректность полей!");
            }
        } else {
            throw new NoSuchElementException("Такого пользователя нет!");
        }
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Проверка на наличие символа @ в мэйле
     * @return true когда символ @ содержится, false когда символа @ нет
     * */
    private boolean checkEmail(String email) {
        return email.contains("@");
    }

    /**
     * Проверка на наличие пробела в логине
     * @return true если пробелов нет, false если он есть
     */
    private boolean checkLogin(String login) {
        return !login.contains(" ");
    }

    /**
     * Проверка на корректность даты рождения
     * @return true если дата рождения была в прошлом, false если дата в будущем
     * */
    private boolean checkBirthday(LocalDateTime birthday) {
        return birthday.isBefore(LocalDateTime.now());
    }
}
