package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IsAlreadyFriendException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public User createUser(final User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(getNextId());
        userStorage.add(user);

        log.info("User created: {}", user);
        return user;
    }

    public User updateUser(final User user) {
        if (userStorage.exists(user)) {
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                user.setName(user.getLogin());
            }

            userStorage.update(user);
            log.info("User updated: {}", user.getId());
            return user;
        } else {
            log.info("User update failed with noSuchElementException: {}", user);
            throw new NotFoundException("Такого пользователя нет!");
        }
    }

    public List<User> getUsers() {
        log.info("Users list was got");
        return new ArrayList<>(userStorage.getUsers());
    }

    public User setFriend(final long userId, final long friendId) {
        if (!userStorage.exists(userId)) {
            throw new NotFoundException("User with id " + userId + " not found!");
        }
        if (!userStorage.exists(friendId)) {
            throw new NotFoundException("Friend with id " + friendId + " not found!");
        }
        if (userStorage.get(userId).getFriends().contains(friendId)) {
            throw new IsAlreadyFriendException("Humans with id " + userId + " and " + friendId + " are already friends!");
        }

        userStorage.get(friendId).putFriend(userId);
        userStorage.get(userId).putFriend(friendId);

        return userStorage.get(userId);
    }

    public User deleteFriend(final long userId, final long friendId) {
        if (!userStorage.exists(userId)) {
            throw new NotFoundException("User with id " + userId + " not found!");
        }
        if (!userStorage.exists(friendId)) {
            throw new NotFoundException("Friend with id " + friendId + " not found!");
        }

        userStorage.get(userId).deleteFriend(friendId);
        userStorage.get(friendId).deleteFriend(userId);

        return userStorage.get(userId);
    }

    public List<User> getFriends(final long userId) {
        if (!userStorage.exists(userId)) {
            throw new NotFoundException("User with id " + userId + " not found!");
        }
        Set<Long> friends = userStorage.get(userId).getFriends();
        return userStorage.getUsers().stream().filter(user -> friends.contains(user.getId())).toList();
    }

    public List<User> getCommonFriends(final long userId, final long friendId) {
        Set<Long> f = userStorage.get(friendId).getFriends();
        return userStorage.get(userId).getFriends().stream().filter(f::contains).map(userStorage::get).toList();
    }

    public boolean containsUser(final long userId) {
        return userStorage.exists(userId);
    }

    private long getNextId() {
        long currentMaxId = userStorage.getUserIds()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
