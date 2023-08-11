package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

public interface UserRepositoryImpl extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);
}
