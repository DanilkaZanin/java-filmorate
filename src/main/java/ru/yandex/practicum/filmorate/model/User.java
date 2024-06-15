package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class User {
    @NonNull
    String email;
    @NonNull
    String login;

    int id;
    String name;
    LocalDateTime birthday;
}
