package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.UserEmailDuplicateException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private static final String LOG_MESSAGE = "Сервис пользователей получил запрос на ";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto addUser(User user) {
        log.info(LOG_MESSAGE + "добавление пользователя: " + user);
        validateUser(user);
        return UserMapper.toUserDto(userRepository.addUser(user));
    }

    public UserDto updateUser(Long userId, User user) {
        log.info(LOG_MESSAGE + "обновление пользователя: " + user + "; c id: " + userId);
        user.setId(userId);
        validateUser(user);
        if (user.getName() == null) {
            user.setName(userRepository.getUser(user.getId()).getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(userRepository.getUser(user.getId()).getEmail());
        }
        return UserMapper.toUserDto(userRepository.updateUser(user));
    }

    public void deleteUser(Long userId) {
        log.info(LOG_MESSAGE + "удаление пользователя с id: " + userId);
        userRepository.deleteUser(userId);
    }

    public UserDto getUser(Long userId) {
        log.info(LOG_MESSAGE + "получение пользователя с id: " + userId);
        return UserMapper.toUserDto(userRepository.getUser(userId));
    }

    public List<UserDto> getUsers() {
        log.info(LOG_MESSAGE + "получение всех пользователей");
        return userRepository.getUsers().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    private void validateUser(User user) {
        log.info("Начата процедура валидации пользователя: " + user);
        userRepository.getUsers().stream()
                .filter(listUser -> listUser.getEmail().equals(user.getEmail()) &&
                        !listUser.getId().equals(user.getId()))
                .findFirst()
                .ifPresent(listUser -> {
                    throw new UserEmailDuplicateException("адрес электронной почты используется другим пользователем");
                });
    }
}
