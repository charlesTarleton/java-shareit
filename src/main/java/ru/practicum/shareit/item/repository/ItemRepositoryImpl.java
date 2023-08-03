package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryImpl extends JpaRepository<Item, Long> {

    Optional<Item> findById(Long id);

    List<Item> findAllByOwner(User owner);

    @Query("SELECT i " +
            "FROM Item AS i " +
            "WHERE (LOWER(i.name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(i.description) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "AND i.available = true")
    List<Item> findAllByText(String text);
}
