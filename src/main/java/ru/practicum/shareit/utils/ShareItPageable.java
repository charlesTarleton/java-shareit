package ru.practicum.shareit.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.exceptions.PageableException;

public class ShareItPageable extends PageRequest {
    public ShareItPageable(int from, int size, Sort sort) {
        super(from, size, sort);
    }

    public static ShareItPageable checkPageable(Integer from, Integer size, Sort sort) {
        if (from == null) {
            from = 0;
        }
        if (size == null) {
            size = 20;
        }
        if (size <= 0 || from < 0) {
            throw new PageableException("Ошибка. Значение from не может быть меньше 0, а size меньше 1");
        }
        return new ShareItPageable(from, size, sort);
    }
}
