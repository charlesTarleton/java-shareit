package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
@Slf4j
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> itemsMap = new LinkedHashMap<>();
    private long currentItemId = 1;
    private static final String LOG_MESSAGE = "Хранилище предметов получило запрос на ";
    private static final String LOG_SUCCESS_MESSAGE = "Успешно выполнена функция: ";

    public Item addItem(Item item) {
        log.info(LOG_MESSAGE + "добавление предмета: " + item);
        item.setId(currentItemId++);
        itemsMap.put(item.getId(), item);
        log.info(LOG_SUCCESS_MESSAGE + "добавление предмета: " + item);
        return item;
    }

    public Item updateItem(Item item) {
        log.info(LOG_MESSAGE + "обновление предмета: " + item + "; c id: " + item.getId());
        itemsMap.put(item.getId(), item);
        log.info(LOG_SUCCESS_MESSAGE + "обновление предмета: " + item + "; c id: " + item.getId());
        return item;
    }

    public void deleteItem(Long itemId) {
        log.info(LOG_MESSAGE + "удаление предмета с id: " + itemId);
        log.info(LOG_SUCCESS_MESSAGE + "удаление предмета с id: " + itemId);
        itemsMap.remove(itemId);
    }

    public Item getItem(Long itemId) {
        log.info(LOG_MESSAGE + "получение предмета с id: " + itemId);
        log.info(LOG_SUCCESS_MESSAGE + "получение предмета с id: " + itemId);
        return itemsMap.get(itemId);
    }

    public List<Item> getItems() {
        log.info(LOG_MESSAGE + "получение всех предметов");
        return new ArrayList<>(itemsMap.values());
    }
}
