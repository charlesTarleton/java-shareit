package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemServiceInterface {
    public ItemDto addItem(ItemDto itemDto, Long owner);

    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long owner);

    public void deleteItem(Long itemId, Long owner);

    public ItemDto getItem(Long itemId);

    public List<ItemDto> getItemsByOwner(Long owner);

    public List<ItemDto> getItemsByName(String name);
}
