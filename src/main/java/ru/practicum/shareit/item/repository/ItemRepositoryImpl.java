package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ItemRepositoryImpl extends JpaRepository<Item, Long> {

    Optional<Item> findById(Long id);

    List<Item> findAllByOwnerId(Long id);
}
