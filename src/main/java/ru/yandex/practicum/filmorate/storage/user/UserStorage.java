package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {
    void add(User user);

    void update(User user);

    void delete(User user);

    boolean exists(User user);

    boolean exists(long userId);

    User get(long userId);

    Collection<User> getUsers();

    Set<Long> getUserIds();
}
