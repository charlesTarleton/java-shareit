package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.UserExistException;
import ru.practicum.shareit.item.repository.CommentRepositoryImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;
import ru.practicum.shareit.user.userUtils.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepositoryImpl userRepository;
    private final CommentRepositoryImpl commentRepository;
    private static final String LOG_MESSAGE = "Сервис пользователей получил запрос на {}, {}";

    public UserDto addUser(UserDto userDto) {
        log.info(LOG_MESSAGE, "добавление пользователя: ", userDto);
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)), List.of());
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        log.info(LOG_MESSAGE, "обновление пользователя c id: ", userId);
        userDto.setId(userId);
        if (userDto.getName() == null) {
            userDto.setName(userRepository.findById(userId).orElseThrow().getName());
        }
        if (userDto.getEmail() == null) {
            userDto.setEmail(userRepository.findById(userId).orElseThrow().getEmail());
        }
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.save(user),
                commentRepository.findAllByAuthor(user));
    }

    public void deleteUser(Long userId) {
        log.info(LOG_MESSAGE, "удаление пользователя с id: ", userId);
        userRepository.deleteById(userId);
    }

    public UserDto getUser(Long userId) {
        log.info(LOG_MESSAGE, "получение пользователя с id: ", userId);
        checkUserExist(userId);
        User user = userRepository.findById(userId).orElseThrow();
        return UserMapper.toUserDto(user, commentRepository.findAllByAuthor(user));
    }

    public List<UserDto> getUsers() {
        log.info(LOG_MESSAGE, "получение всех пользователей", "");
        return userRepository.findAll().stream()
                .map(user -> UserMapper.toUserDto(user, commentRepository.findAllByAuthor(user)))
                .collect(Collectors.toList());
    }

    private void checkUserExist(Long ownerId) {
        log.info("Начата процедура проверки наличия в репозитории пользователя с id: {}", ownerId);
        if (userRepository.findById(ownerId).isEmpty()) {
            throw new UserExistException("Ошибка. Запрошенного пользователя в базе данных не существует");
        }
    }
}
