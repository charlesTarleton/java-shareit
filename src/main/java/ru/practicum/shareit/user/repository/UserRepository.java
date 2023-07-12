package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserRepository {
    User addUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    User getUser(Long userId);

    List<User> getUsers();
}
