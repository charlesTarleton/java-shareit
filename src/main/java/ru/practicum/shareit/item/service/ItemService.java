package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ItemWithWrongOwner;
import ru.practicum.shareit.exceptions.ItemWithoutOwnerException;
import ru.practicum.shareit.item.itemUtils.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService implements ItemServiceInterface {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private static final String LOG_MESSAGE = "Сервис предметов получил запрос на {}, {}";
    private static final String LOG_SUCCESS_MESSAGE = "Успешно выполнена функция: {}, {}";

    public ItemDto addItem(ItemDto itemDto, Long owner) {
        log.info(LOG_MESSAGE, "добавление предмета: ", itemDto);
        checkUserExist(owner);
        return ItemMapper.toItemDto(itemRepository.addItem(ItemMapper.toItem(null, itemDto, owner), owner));
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long owner) {
        log.info(LOG_MESSAGE, "обновление предмета c id: ", itemId);
        checkUserExist(owner);
        checkUserIsOwner(itemId, owner);
        if (itemDto.getName() == null) {
            itemDto.setName(itemRepository.getItem(itemId).getName());
        }
        if (itemDto.getDescription() == null) {
            itemDto.setDescription(itemRepository.getItem(itemId).getDescription());
        }
        if (itemDto.getAvailable() == null) {
            itemDto.setAvailable(itemRepository.getItem(itemId).getAvailable());
        }
        return ItemMapper.toItemDto(itemRepository.updateItem(itemId, ItemMapper
                .toItem(itemId, itemDto, owner), owner));
    }

    public void deleteItem(Long itemId, Long owner) {
        log.info(LOG_MESSAGE, "удаление предмета с id: ", itemId);
        checkUserExist(owner);
        checkUserIsOwner(itemId, owner);
        itemRepository.deleteItem(itemId, owner);
    }

    public ItemDto getItem(Long itemId) {
        log.info(LOG_MESSAGE, "получение предмета с id: ", itemId);
        return ItemMapper.toItemDto(itemRepository.getItem(itemId));
    }

    public List<ItemDto> getItemsByOwner(Long owner) {
        log.info(LOG_MESSAGE, "получение всех предметов пользователя с id: ", owner);
        checkUserExist(owner);
        log.info(LOG_SUCCESS_MESSAGE, "получение всех предметов пользователя с id: ", owner);
        return itemRepository.getItemsByOwner(owner).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> getItemsByName(String name) {
        log.info(LOG_MESSAGE, "получение всех предметов, содержащих в названии: ", name);
        log.info(LOG_SUCCESS_MESSAGE, "получение всех предметов, содержащих в названии: ", name);
        if (name.isBlank()) {
            return List.of();
        }
        String lowerName = name.toLowerCase();
        return itemRepository.getItems().stream()
                .filter((item -> item.getAvailable() && (item.getName().toLowerCase().contains(lowerName) ||
                        item.getDescription().toLowerCase().contains(lowerName))))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private void checkUserExist(Long owner) {
        log.info("Начата процедура проверки наличия в репозитории пользователя с id: {}", owner);
        if (owner == null || userRepository.getUser(owner) == null) {
            throw new ItemWithoutOwnerException("Ошибка. " +
                    "Создать/обновить/удалить предмет может только зарегистрированный в приложении пользователь");
        }
    }

    private void checkUserIsOwner(Long itemId, Long owner) {
        log.info("Начата процедура проверки принадлежности предмета с id: {} пользователю с id: {}", itemId, owner);
        if (!itemRepository.getItem(itemId).getOwner().equals(owner)) {
            throw new ItemWithWrongOwner("Ошибка. Создать/обновить/удалить предмет может только владелец предмета");
        }
    }
}
