package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.UserEmailDuplicateException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
@Slf4j
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> usersMap = new LinkedHashMap<>();
    private final Map<Long, String> emailsMap = new HashMap<>();
    private long currentUserId = 1;
    private static final String LOG_MESSAGE = "Хранилище пользователей получило запрос на {} {}";
    private static final String LOG_SUCCESS_MESSAGE = "Успешно выполнена функция: {} {}";

    public User addUser(User user) {
        log.info(LOG_MESSAGE, "добавление пользователя: ", user);
        user.setId(currentUserId);
        emailsMap.put(currentUserId, validateUserEmail(user.getEmail()));
        usersMap.put(currentUserId++, user);
        log.info(LOG_SUCCESS_MESSAGE, "добавление пользователя: ", user);
        return user;
    }

    public User updateUser(Long userId, User user) {
        log.info(LOG_MESSAGE, "обновление пользователя c id: ", userId);
        emailsMap.remove(userId);
        emailsMap.put(userId, validateUserEmail(user.getEmail()));
        usersMap.put(userId, user);
        log.info(LOG_SUCCESS_MESSAGE, "обновление пользователя: ", user);
        return user;
    }

    public void deleteUser(Long userId) {
        log.info(LOG_MESSAGE, "удаление пользователя с id: ", userId);
        emailsMap.remove(userId);
        usersMap.remove(userId);
        log.info(LOG_SUCCESS_MESSAGE, "удаление пользователя с id: ", userId);
    }

    public User getUser(Long userId) {
        log.info(LOG_MESSAGE, "получение пользователя с id: ", userId);
        log.info(LOG_SUCCESS_MESSAGE, "получение пользователя с id: ", userId);
        return usersMap.get(userId);
    }

    public List<User> getUsers() {
        log.info(LOG_MESSAGE, "получение всех пользователей", "");
        log.info(LOG_SUCCESS_MESSAGE, "получение всех пользователей", "");
        return new ArrayList<>(usersMap.values());
    }
    
    private String validateUserEmail(String email) {
        log.info("Начата процедура проверки эл. почты {} на дублирование", email);
        if (emailsMap.containsValue(email)) {
            throw new UserEmailDuplicateException("Ошибка. Адрес электронной почты занят");
        }
        return email;
    }
}
