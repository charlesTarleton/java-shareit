package ru.practicum.shareit.item.itemUtils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Slf4j
@UtilityClass
public class ItemMapper {
    public ItemDto toItemDto(Item item, List<CommentDto> comments) {
        log.info("Начата процедура преобразования предмета в ДТО: {}", item);
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                comments,
                null,
                null);
    }

    public ItemDto toItemDtoGetMethod(Item item, List<CommentDto> comments,
                                      BookingItemDto startBookingDto, BookingItemDto endBookingDto) {
        log.info("Начата процедура преобразования предмета в ДТО: {}", item);
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                comments,
                startBookingDto,
                endBookingDto);
    }

    public Item toItem(Long itemId, ItemDto itemDto, User owner) {
        log.info("Начата процедура преобразования ДТО в предмет: {}", itemDto);
        return new Item(
                itemId,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                owner);
    }
}
