package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.repository.BookingRepositoryImpl;
import ru.practicum.shareit.exceptions.ItemExistException;
import ru.practicum.shareit.exceptions.ItemWithWrongOwner;
import ru.practicum.shareit.exceptions.UserExistException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.itemUtils.CommentMapper;
import ru.practicum.shareit.item.itemUtils.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepositoryImpl;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;

import java.time.LocalDateTime;
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
    private final BookingRepositoryImpl bookingRepository;

    private static final String LOG_MESSAGE = "Сервис предметов получил запрос на {}{}";

    public ItemDto addItem(ItemDto itemDto, Long ownerId) {
        log.info(LOG_MESSAGE, "добавление предмета: ", itemDto);
        User owner = checkUserExist(ownerId);
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper
                .toItem(null, itemDto, owner)), List.of());
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long ownerId) {
        log.info(LOG_MESSAGE, "обновление предмета c id: ", itemId);
        User owner = checkUserExist(ownerId);
        Item currentBDItem = checkItemExist(itemId);
        Item item = ItemMapper.toItem(itemId, itemDto, owner);
        if (item.getName() == null) {
            item.setName(currentBDItem.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(currentBDItem.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(currentBDItem.getAvailable());
        }
        checkUserIsOwner(itemId, ownerId);
        return ItemMapper.toItemDto(itemRepository.save(item), commentRepository.findAllByItem(item));
    }

    public void deleteItem(Long itemId, Long ownerId) {
        log.info(LOG_MESSAGE, "удаление предмета с id: ", itemId);
        checkUserExist(ownerId);
        checkUserIsOwner(itemId, ownerId);
        itemRepository.deleteById(itemId);
    }

    public ItemDto getItem(Long itemId) {
        log.info(LOG_MESSAGE, "получение предмета с id: ", itemId);
        Item item = checkItemExist(itemId);
        return ItemMapper.toItemDtoGetMethod(
                item,
                commentRepository.findAllByItem(item),
                bookingRepository.findFirstByEndBeforeAndItemIdOrderByEndDesc(LocalDateTime.now(), itemId),
                bookingRepository.findFirstByStartAfterAndItemIdOrderByStartAsc(LocalDateTime.now(), itemId));
    }

    public List<ItemDto> getItemsByOwner(Long ownerId) {
        log.info(LOG_MESSAGE, "получение предметов по пользователю с id: ", ownerId);
        User owner = userRepository.findById(ownerId).orElseThrow();
        return itemRepository.findAllByOwner(owner).stream()
                .map(item -> ItemMapper.toItemDto(item, commentRepository.findAllByItem(item)))
                .collect(Collectors.toList());
    }

    public List<ItemDto> getItemsByName(String text) { // сервис
        log.info(LOG_MESSAGE, "получение предметов по названию: ", text);
        if (text.isBlank()) {
            return List.of();
        }
        return itemRepository.findAllByText(text).stream()
                .map(item -> ItemMapper.toItemDto(item, commentRepository.findAllByItem(item)))
                .collect(Collectors.toList());
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
            throw new UserExistException("Ошибка. Запрошенного пользователя в базе данных не существует");
        }
        return userOptional.orElseThrow();
    }

    private Item checkItemExist(Long itemId) {
        log.info("Начата процедура проверки наличия в репозитории предмета с id: {}", itemId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new ItemExistException("Ошибка. Запрошенного предмета в базе данных не существует");
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