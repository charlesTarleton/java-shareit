package ru.practicum.shareit.utils;

public final class ConstantaStorage {
    public static final class Common {
        public static final String USER_HEADER = "X-Sharer-User-Id";
    }

    public static final class Booking {
        public static final String CONTROLLER_LOG = "Контроллер бронирования получил запрос на {}{}";
        public static final String SERVICE_LOG = "Сервис бронирования получил запрос на {}{}";
    }

    public static final class Item {
        public static final String CONTROLLER_LOG = "Контроллер предметов получил запрос на {}{}";
        public static final String SERVICE_LOG = "Сервис предметов получил запрос на {}{}";
    }

    public static final class User {
        public static final String CONTROLLER_LOG = "Контроллер пользователей получил запрос на {}{}";
        public static final String SERVICE_LOG = "Сервис пользователей получил запрос на {}{}";
    }

    public static final class Error {
        public static final String ERROR_400 = "Ошибка 400";
        public static final String ERROR_400_DESCRIPTION = "Ошибка валидации";

        public static final String ERROR_404 = "Ошибка 404";
        public static final String ERROR_404_DESCRIPTION = "Искомый объект не найден";

        public static final String ERROR_500 = "Unknown state: UNSUPPORTED_STATUS";
        public static final String ERROR_500_DESCRIPTION = "Возникло исключение";
    }

    public static final class Request {
        public static final String CONTROLLER_LOG = "Контроллер запросов получил запрос на {}{}";
        public static final String SERVICE_LOG = "Сервис запросов получил запрос на {}{}";
    }
}
