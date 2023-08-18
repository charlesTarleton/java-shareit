package ru.practicum.shareit.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exceptions.*;

import javax.validation.ValidationException;

import static ru.practicum.shareit.utils.ConstantaStorage.Error.*;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler({ItemNotAvailableException.class, MethodArgumentNotValidException.class,
            ValidationException.class, BookingChangeStatusException.class, CommentBookerException.class,
            PageableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse fourHundredErrorHandle(final Exception exception) {
        log.warn(ERROR_400, exception);
        return new ErrorResponse(ERROR_400, ERROR_400_DESCRIPTION);
    }

    @ExceptionHandler({ItemWithWrongOwner.class, ItemWithoutOwnerException.class, UserExistException.class,
            ItemExistException.class, BookingExistException.class, RequestExistException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse fourHundredFourErrorHandle(Exception exception) {
        log.warn(ERROR_404, exception);
        return new ErrorResponse(ERROR_404, ConstantaStorage.Error.ERROR_404_DESCRIPTION);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse fiveHundredErrorHandle(final Throwable exception) {
        log.warn(ERROR_500, exception);
        return new ErrorResponse(ERROR_500, ERROR_500_DESCRIPTION);
    }

    @Getter
    class ErrorResponse {
        private final String error;
        private final String description;

        public ErrorResponse(String error, String description) {
            this.error = error;
            this.description = description;
        }
    }
}