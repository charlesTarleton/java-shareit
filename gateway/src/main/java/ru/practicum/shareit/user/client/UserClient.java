package ru.practicum.shareit.user.client;

import ru.practicum.shareit.client.BaseClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.user.dto.GatewayUserDto;

@Service
@Slf4j
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";
    private static final String CLIENT_LOG = "Клиент пользователей получил запрос на {}{}";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addUser(GatewayUserDto userDto) {
        log.info(CLIENT_LOG, "добавление пользователя: ", userDto);
        return post("", userDto);
    }

    public ResponseEntity<Object> updateUser(Long userId, GatewayUserDto userDto) {
        log.info(CLIENT_LOG, "обновление пользователя c id: ", userId);
        return patch("/" + userId, userDto);
    }

    public void deleteUser(Long userId) {
        log.info(CLIENT_LOG, "удаление пользователя с id: ", userId);
        delete("/" + userId);
    }

    public ResponseEntity<Object> getUser(Long userId) {
        log.info(CLIENT_LOG, "получение пользователя с id: ", userId);
        return get("/" + userId);
    }

    public ResponseEntity<Object> getUsers(Integer from, Integer size) {
        log.info(CLIENT_LOG, "получение всех пользователей", "");
        return get("");
    }
}