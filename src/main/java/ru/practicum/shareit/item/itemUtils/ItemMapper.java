package ru.practicum.shareit.item.itemUtils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Slf4j
@UtilityClass
public class ItemMapper {
    public ItemDto toItemDto(Item item) {
        log.info("Начата процедура преобразования предмета в ДТО: {}", item);
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable());
    }

    public Item toItem(Long itemId, ItemDto itemDto, Long owner) {
        log.info("Начата процедура преобразования ДТО в предмет: {}", itemDto);
        return new Item(
                itemId,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                owner,
                null); // заглушка до ТЗ с созданием запросов
    }
}
