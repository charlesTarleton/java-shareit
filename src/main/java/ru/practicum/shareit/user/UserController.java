package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private static final String LOG_MESSAGE = "Контроллер пользователей получил запрос на ";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody User user) {
        log.info(LOG_MESSAGE + "добавление пользователя: " + user);
        return userService.addUser(user);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
        log.info(LOG_MESSAGE + "обновление пользователя: " + user + "; c id: " + userId);
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        log.info(LOG_MESSAGE + "удаление пользователя с id: " + userId);
        userService.deleteUser(userId);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long userId) {
        log.info(LOG_MESSAGE + "получение пользователя с id: " + userId);
        return userService.getUser(userId);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        log.info(LOG_MESSAGE + "получение всех пользователей");
        return userService.getUsers();
    }
}
