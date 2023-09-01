package ru.practicum.shareit.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exceptions.GatewayIllegalBookingStateException;

import javax.validation.ValidationException;

@RestControllerAdvice
@Slf4j
@Validated
public class GatewayControllerAdvice {
    public static final String ERROR_400 = "Unknown state: UNSUPPORTED_STATUS";
    public static final String ERROR_400_DESCRIPTION = "Ошибка валидации";

    public static final String ERROR_500 = "Unknown state: UNSUPPORTED_STATUS";
    public static final String ERROR_500_DESCRIPTION = "Возникло исключение";

    @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class,
            GatewayIllegalBookingStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GatewayErrorResponse fourHundredErrorHandle(final Exception exception) {
        log.warn(ERROR_400, exception);
        return new GatewayErrorResponse(ERROR_400, ERROR_400_DESCRIPTION);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GatewayErrorResponse fiveHundredErrorHandle(final Throwable exception) {
        log.warn(ERROR_500, exception);
        return new GatewayErrorResponse(ERROR_500, ERROR_500_DESCRIPTION);
    }
}