package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item addItem(Item item);

    Item updateItem(Item item);

    void deleteItem(Long itemId);

    Item getItem(Long itemId);

    List<Item> getItems();
}
