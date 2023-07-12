package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ItemWithWrongOwner;
import ru.practicum.shareit.exceptions.ItemWithoutOwnerException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private static final String LOG_MESSAGE = "Сервис предметов получил запрос на ";
    private static final String LOG_SUCCESS_MESSAGE = "Успешно выполнена функция: ";

    @Autowired
    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ItemDto addItem(Item item, Long owner) {
        log.info(LOG_MESSAGE + "добавление предмета: " + item);
        checkUserExist(owner);
        item.setOwner(owner);
        return ItemMapper.toItemDto(itemRepository.addItem(item));
    }

    public ItemDto updateItem(Long itemId, Item item, Long owner) {
        log.info(LOG_MESSAGE + "обновление предмета: " + item + "; c id: " + itemId);
        checkUserExist(owner);
        checkUserIsOwner(itemId, owner);
        if (item.getName() == null) {
            item.setName(itemRepository.getItem(itemId).getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(itemRepository.getItem(itemId).getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(itemRepository.getItem(itemId).getAvailable());
        }
        item.setId(itemId);
        item.setOwner(itemRepository.getItem(itemId).getOwner());
        item.setRequest(itemRepository.getItem(itemId).getRequest());
        return ItemMapper.toItemDto(itemRepository.updateItem(item));
    }

    public void deleteItem(Long itemId, Long owner) {
        log.info(LOG_MESSAGE + "удаление предмета с id: " + itemId);
        checkUserExist(owner);
        checkUserIsOwner(itemId, owner);
        itemRepository.deleteItem(itemId);
    }

    public ItemDto getItem(Long itemId) {
        log.info(LOG_MESSAGE + "получение предмета с id: " + itemId);
        return ItemMapper.toItemDto(itemRepository.getItem(itemId));
    }

    public List<ItemDto> getItemsByOwner(Long owner) {
        log.info(LOG_MESSAGE + "получение всех предметов пользователя с id: " + owner);
        checkUserExist(owner);
        log.info(LOG_SUCCESS_MESSAGE + "получение всех предметов пользователя с id: " + owner);
        return itemRepository.getItems().stream()
                .filter(item -> item.getOwner().equals(owner))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> getItemsByName(String name) {
        log.info(LOG_MESSAGE + "получение всех предметов, содержащих в названии: " + name);
        log.info(LOG_SUCCESS_MESSAGE + "получение всех предметов, содержащих в названии: " + name);
        if (name.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.getItems().stream()
                .filter((item -> item.getName().toLowerCase().contains(name.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(name.toLowerCase()) && item.getAvailable()))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private void checkUserExist(Long owner) {
        log.info("Начата процедура проверки наличия в репозитории пользователя с id: " + owner);
        if (owner == null || userRepository.getUser(owner) == null) {
            throw new ItemWithoutOwnerException();
        }
    }

    private void checkUserIsOwner(Long itemId, Long owner) {
        log.info("Начата процедура проверки принадлежности предмета с id: " + itemId + " пользователю с id: " + owner);
        if (!itemRepository.getItem(itemId).getOwner().equals(owner)) {
            throw new ItemWithWrongOwner();
        }
    }
}
