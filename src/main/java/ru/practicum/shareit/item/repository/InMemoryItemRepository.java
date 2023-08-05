package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
@Slf4j
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> itemsById = new LinkedHashMap<>();
    private final Map<Long, List<Item>> itemsByOwnerId = new LinkedHashMap<>();
    private long currentItemId = 1;
    private static final String LOG_MESSAGE = "Хранилище предметов получило запрос на {}, {}";
    private static final String LOG_SUCCESS_MESSAGE = "Успешно выполнена функция: {}, {}";

    public Item addItem(Item item, Long owner) {
        log.info(LOG_MESSAGE, "добавление предмета: ", item);
        item.setId(currentItemId);
        itemsById.put(currentItemId++, item);
        List<Item> itemList;
        if (itemsByOwnerId.containsKey(owner)) {
            itemList = itemsByOwnerId.get(owner);
        } else {
            itemList = new ArrayList<>();
        }
        itemList.add(item);
        itemsByOwnerId.put(owner, itemList);
        log.info(LOG_SUCCESS_MESSAGE, "добавление предмета: ", item);
        return item;
    }

    public Item updateItem(Long itemId, Item item, Long owner) {
        log.info(LOG_MESSAGE, "обновление предмета c id: ", itemId);
        item.setRequest(itemsById.get(itemId).getRequest());
        itemsByOwnerId.get(owner).remove(itemsById.get(itemId));
        itemsByOwnerId.get(owner).add(item);
        itemsById.put(itemId, item);
        log.info(LOG_SUCCESS_MESSAGE, "обновление предмета: ", item);
        return item;
    }

    public void deleteItem(Long itemId, Long owner) {
        log.info(LOG_MESSAGE, "удаление предмета с id: ", itemId);
        itemsByOwnerId.get(owner).remove(itemsById.get(itemId));
        log.info(LOG_SUCCESS_MESSAGE, "удаление предмета с id: ", itemId);
        itemsById.remove(itemId);
    }

    public Item getItem(Long itemId) {
        log.info(LOG_MESSAGE, "получение предмета с id: ", itemId);
        log.info(LOG_SUCCESS_MESSAGE, "получение предмета с id: ", itemId);
        return itemsById.get(itemId);
    }

    public List<Item> getItemsByOwner(Long owner) {
        log.info(LOG_MESSAGE, "получение всех предметов пользователя с id: ", owner);
        return itemsByOwnerId.get(owner);
    }

    public List<Item> getItems() {
        log.info(LOG_MESSAGE, "получение всех предметов", "");
        return new ArrayList<>(itemsById.values());
    }
}
