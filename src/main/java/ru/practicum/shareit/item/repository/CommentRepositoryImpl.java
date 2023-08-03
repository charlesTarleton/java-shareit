package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface CommentRepositoryImpl extends JpaRepository<Comment, Long> {

    List<Comment> findAllByAuthor(User user);

    List<Comment> findAllByItem(Item item);
}
