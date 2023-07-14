package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    User addUser(User user);

    User updateUser(Long userId, User user);

    void deleteUser(Long userId);

    User getUser(Long userId);

    List<User> getUsers();
}
