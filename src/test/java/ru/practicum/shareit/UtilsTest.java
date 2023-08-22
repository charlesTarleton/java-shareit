package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.exceptions.ItemNotAvailableException;
import ru.practicum.shareit.exceptions.PageableException;
import ru.practicum.shareit.exceptions.UserExistException;
import ru.practicum.shareit.utils.ControllerAdvice;
import ru.practicum.shareit.utils.ErrorResponse;
import ru.practicum.shareit.utils.ShareItPageable;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {
    @Test
    void checkErrorHandler() {
        ControllerAdvice controllerAdvice = new ControllerAdvice();

        ItemNotAvailableException exception400 = new ItemNotAvailableException("Ошибка 400");
        ErrorResponse errorResponse = controllerAdvice.fourHundredErrorHandle(exception400);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getDescription(), "Ошибка валидации");

        UserExistException exception404 = new UserExistException("404");
        errorResponse = controllerAdvice.fourHundredFourErrorHandle(exception404);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getDescription(), "Искомый объект не найден");

        RuntimeException exception500 = new RuntimeException("Ошибка 500");
        errorResponse = controllerAdvice.fiveHundredErrorHandle(exception500);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getDescription(), "Возникло исключение");
    }

    @Test
    void checkShareItPageable() {
        ShareItPageable pageable = ShareItPageable.checkPageable(null, null, Sort.unsorted());

        assertEquals(0, pageable.getPageNumber());
        assertEquals(20, pageable.getPageSize());
        assertEquals(Sort.unsorted(), pageable.getSort());
        assertThrows(PageableException.class, () -> ShareItPageable.checkPageable(-1, -2, Sort.unsorted()));
    }
}