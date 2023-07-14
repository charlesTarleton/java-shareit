package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final String LOG_MESSAGE = "Контроллер пользователей получил запрос на {} {}";

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        log.info(LOG_MESSAGE, "добавление пользователя: ", userDto);
        return userService.addUser(userDto);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") Long userId, @RequestBody UserDto userDto) {
        log.info(LOG_MESSAGE, "обновление пользователя c id: ", userId);
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        log.info(LOG_MESSAGE, "удаление пользователя с id: ", userId);
        userService.deleteUser(userId);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long userId) {
        log.info(LOG_MESSAGE, "получение пользователя с id: ", userId);
        return userService.getUser(userId);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        log.info(LOG_MESSAGE, "получение всех пользователей", "");
        return userService.getUsers();
    }
}
