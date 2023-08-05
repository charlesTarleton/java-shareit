package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.userUtils.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private static final String LOG_MESSAGE = "Сервис пользователей получил запрос на {}, {}";

    public UserDto addUser(UserDto userDto) {
        log.info(LOG_MESSAGE, "добавление пользователя: ", userDto);
        return UserMapper.toUserDto(userRepository.addUser(UserMapper.toUser(userDto)));
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        log.info(LOG_MESSAGE, "обновление пользователя c id: ", userId);
        userDto.setId(userId);
        if (userDto.getName() == null) {
            userDto.setName(userRepository.getUser(userId).getName());
        }
        if (userDto.getEmail() == null) {
            userDto.setEmail(userRepository.getUser(userId).getEmail());
        }
        return UserMapper.toUserDto(userRepository.updateUser(userId, UserMapper.toUser(userDto)));
    }

    public void deleteUser(Long userId) {
        log.info(LOG_MESSAGE, "удаление пользователя с id: ", userId);
        userRepository.deleteUser(userId);
    }

    public UserDto getUser(Long userId) {
        log.info(LOG_MESSAGE, "получение пользователя с id: ", userId);
        return UserMapper.toUserDto(userRepository.getUser(userId));
    }

    public List<UserDto> getUsers() {
        log.info(LOG_MESSAGE, "получение всех пользователей", "");
        return userRepository.getUsers().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
