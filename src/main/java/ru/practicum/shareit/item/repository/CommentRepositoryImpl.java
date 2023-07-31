package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

@Transactional
public interface CommentRepositoryImpl extends JpaRepository<Comment, Long> {

    List<Comment> findAllByAuthor(Long id);

    List<Comment> findAllByItem(Long id);
}
