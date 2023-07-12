package ru.practicum.shareit.exceptions;

import javax.validation.ValidationException;

public class ItemWithoutOwnerException extends ValidationException {
    public ItemWithoutOwnerException() {
        super("Ошибка. Создать/обновить/удалить предмет может только зарегистрированный в приложении пользователь");
    }
}
