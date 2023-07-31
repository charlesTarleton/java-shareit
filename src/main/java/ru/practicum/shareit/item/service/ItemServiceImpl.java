package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.ItemWithWrongOwner;
import ru.practicum.shareit.exceptions.ItemWithoutOwnerException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.itemUtils.CommentMapper;
import ru.practicum.shareit.item.itemUtils.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepositoryImpl;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepositoryImpl itemRepository;
    private final CommentRepositoryImpl commentRepository;
    private final UserRepositoryImpl userRepository;

    private static final String LOG_MESSAGE = "Сервис предметов получил запрос на {}, {}";

    public ItemDto addItem(ItemDto itemDto, Long ownerId) {
        log.info(LOG_MESSAGE, "добавление предмета: ", itemDto);
        User owner = checkUserExist(ownerId);
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper
                .toItem(null, itemDto, owner)), List.of());
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long ownerId) {
        log.info(LOG_MESSAGE, "обновление предмета c id: ", itemId);
        User owner = checkUserExist(ownerId);
        checkUserIsOwner(itemId, ownerId);
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper
                .toItem(itemId, itemDto, owner)), commentRepository.findAllByItem(itemId));
    }

    public void deleteItem(Long itemId, Long ownerId) {
        log.info(LOG_MESSAGE, "удаление предмета с id: ", itemId);
        checkUserExist(ownerId);
        checkUserIsOwner(itemId, ownerId);
        itemRepository.deleteById(itemId);
    }

    public ItemDto getItem(Long itemId) {
        log.info(LOG_MESSAGE, "получение предмета с id: ", itemId);
        return ItemMapper.toItemDto(itemRepository.findById(itemId).orElseThrow(),
                commentRepository.findAllByItem(itemId));
    }

    public List<ItemDto> getItemsByOwner(Long ownerId) {
        return itemRepository.findAllByOwnerId(ownerId).stream()
                .map(item -> ItemMapper.toItemDto(item, commentRepository.findAllByItem(item.getId())))
                .collect(Collectors.toList());
    }

    public List<ItemDto> getItemsByName(String name) {
        return null;
    }

    public CommentDto addCommentToItem(Long itemId, CommentDto commentDto, Long authorId) {
        log.info(LOG_MESSAGE, "добавление комментария: ", commentDto.getText());
        Item item = checkItemExist(itemId);
        User author = checkUserExist(authorId);
        return CommentMapper.toCommentDto(commentRepository.save(CommentMapper
                .toComment(commentDto, item, author)));
    }

    private User checkUserExist(Long ownerId) {
        log.info("Начата процедура проверки наличия в репозитории пользователя с id: {}", ownerId);
        Optional<User> userOptional = userRepository.findById(ownerId);
        if (userOptional.isEmpty()) {
            throw new ItemWithoutOwnerException("Ошибка. " +
                    "Создать/обновить/удалить предмет может только зарегистрированный в приложении пользователь");
        }
        return userOptional.orElseThrow();
    }

    private Item checkItemExist(Long itemId) {
        log.info("Начата процедура проверки наличия в репозитории предмета с id: {}", itemId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new ItemWithoutOwnerException("Ошибка. " +
                    "Создать/обновить/удалить предмет может только зарегистрированный в приложении пользователь");
        }
        return itemOptional.orElseThrow();
    }

    private void checkUserIsOwner(Long itemId, Long ownerId) {
        log.info("Начата процедура проверки принадлежности предмета с id: {} пользователю с id: {}", itemId, ownerId);
        if (!itemRepository.findById(itemId).orElseThrow().getOwner().getId().equals(ownerId)) {
            throw new ItemWithWrongOwner("Ошибка. Обновить/удалить предмет может только владелец предмета");
        }
    }
}