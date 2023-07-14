package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item addItem(Item item, Long owner);

    Item updateItem(Long itemId, Item item, Long owner);

    void deleteItem(Long itemId, Long owner);

    Item getItem(Long itemId);

    List<Item> getItems();

    List<Item> getItemsByOwner(Long owner);
}
