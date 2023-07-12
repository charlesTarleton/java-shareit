package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> usersMap = new LinkedHashMap<>();
    private long currentUserId = 1;
    private static final String LOG_MESSAGE = "Хранилище пользователей получило запрос на ";
    private static final String LOG_SUCCESS_MESSAGE = "Успешно выполнена функция: ";

    public User addUser(User user) {
        log.info(LOG_MESSAGE + "добавление пользователя: " + user);
        user.setId(currentUserId++);
        usersMap.put(user.getId(), user);
        log.info(LOG_SUCCESS_MESSAGE + "добавление пользователя: " + user);
        return user;
    }

    public User updateUser(User user) {
        log.info(LOG_MESSAGE + "обновление пользователя: " + user + "; c id: " + user.getId());
        usersMap.put(user.getId(), user);
        log.info(LOG_SUCCESS_MESSAGE + "обновление пользователя: " + user + "; c id: " + user.getId());
        return user;
    }

    public void deleteUser(Long userId) {
        log.info(LOG_MESSAGE + "удаление пользователя с id: " + userId);
        log.info(LOG_SUCCESS_MESSAGE + "удаление пользователя с id: " + userId);
        usersMap.remove(userId);
    }

    public User getUser(Long userId) throws NullPointerException {
        log.info(LOG_MESSAGE + "получение пользователя с id: " + userId);
        log.info(LOG_SUCCESS_MESSAGE + "получение пользователя с id: " + userId);
        return usersMap.get(userId);
    }

    public List<User> getUsers() {
        log.info(LOG_MESSAGE + "получение всех пользователей");
        log.info(LOG_SUCCESS_MESSAGE + "получение всех пользователей");
        return new ArrayList<>(usersMap.values());
    }
}
