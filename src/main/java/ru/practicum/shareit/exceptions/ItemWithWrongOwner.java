package ru.practicum.shareit.exceptions;

public class ItemWithWrongOwner extends RuntimeException {
    public ItemWithWrongOwner() {
        super("Ошибка. Создать/обновить/удалить предмет может только владелец предмета");
    }
}
