package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    public UserDto addUser(UserDto userDto);

    public UserDto updateUser(Long userId, UserDto userDto);

    public void deleteUser(Long userId);

    public UserDto getUser(Long userId);

    public List<UserDto> getUsers();
}
