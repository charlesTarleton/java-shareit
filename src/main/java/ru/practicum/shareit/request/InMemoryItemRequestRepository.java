package ru.practicum.shareit.request;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemoryItemRequestRepository {
    private final Set<ItemRequest> itemRequestsList = new HashSet<>();
}
