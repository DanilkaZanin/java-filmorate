package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final HashMap<Long, User> users;

    public InMemoryUserStorage() {
        this.users = new HashMap<>();
    }

    @Override
    public void add(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    public boolean exists(User user) {
        return users.containsKey(user.getId());
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public Set<Long> getUserIds() {
        return users.keySet();
    }
}
