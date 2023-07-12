package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.UserDto;

@Slf4j
public class UserMapper {
    public static UserDto toUserDto(User user) {
        log.info("Начата процедура преобразования пользователя в ДТО: " + user);
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail());
    }
}
