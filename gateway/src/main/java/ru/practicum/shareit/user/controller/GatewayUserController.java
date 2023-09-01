package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.GatewayUserDto;
import ru.practicum.shareit.user.client.UserClient;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@Slf4j
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class GatewayUserController {
    private final UserClient userClient;
    private static final String CONTROLLER_LOG = "Контроллер пользователей получил запрос на {}{}";

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody GatewayUserDto userDto) {
        log.info(CONTROLLER_LOG, "добавление пользователя: ", userDto);
        return userClient.addUser(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long userId,
                                             @RequestBody GatewayUserDto userDto) {
        log.info(CONTROLLER_LOG, "обновление пользователя c id: ", userId);
        return userClient.updateUser(userId, userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable("id") Long userId) {
        log.info(CONTROLLER_LOG, "удаление пользователя с id: ", userId);
        userClient.deleteUser(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") Long userId) {
        log.info(CONTROLLER_LOG, "получение пользователя с id: ", userId);
        return userClient.getUser(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers(
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {
        log.info(CONTROLLER_LOG, "получение всех пользователей", "");
        return userClient.getUsers(from, size);
    }
}