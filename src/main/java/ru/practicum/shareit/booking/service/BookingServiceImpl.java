package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.bookingUtils.BookingMapper;
import ru.practicum.shareit.booking.bookingUtils.BookingState;
import ru.practicum.shareit.booking.bookingUtils.BookingStatus;
import ru.practicum.shareit.booking.dto.ReceivedBookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepositoryImpl;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepositoryImpl bookingRepository;
    private final UserRepositoryImpl userRepository;
    private final ItemRepositoryImpl itemRepository;

    public ReturnBookingDto addBooking(ReceivedBookingDto bookingDto, Long userId) {
        Item item = checkItemExist(bookingDto.getItemId());
        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) {
            throw new BookingDateValidationException("Ошибка. Даты бронирования не могут содержать null");
        }
        if (!item.getAvailable()) {
            throw new ItemNotAvailableException("Ошибка. Предмет не доступен для бронирования");
        }
        if (!bookingDto.getStart().isBefore(bookingDto.getEnd())) {
            throw new BookingDateValidationException("Ошибка. Дата начала бронирования должна быть раньше конца");
        }
        if (item.getOwner().getId().longValue() == userId) {
            throw new ItemWithoutOwnerException("Ошибка. Владелец вещи не может направить запрос на ее аренду");
        }
        User user = checkUserExist(userId);
        Booking booking = BookingMapper.toBookingFromReceivedDto(bookingDto, item, user);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    public ReturnBookingDto setBookingStatus(Long bookingId, Boolean status, Long ownerId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        checkUserIsOwner(ownerId, booking.getItem().getOwner().getId());
        if (!booking.getStatus().equals(BookingStatus.WAITING)) {
            throw new BookingChangeStatusException("Ошибка. Повторное принятие решения по брони не допускается");
        }
        if (status) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    public ReturnBookingDto getBooking(Long bookingId, Long userId) {
        checkUserExist(userId);
        Booking booking = checkBookingExist(bookingId);
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new UserExistException("Ошибка. Запрашивать данные о брони может только причастное к ней лицо");
        }
        return BookingMapper.toBookingDto(bookingRepository.findById(bookingId).orElseThrow());
    }

    public List<ReturnBookingDto> getBookerBookings(BookingState state, Long bookerId) {
        checkUserExist(bookerId);
        List<Booking> bookings;
        switch (state) {
            case CURRENT:
                bookings = bookingRepository.findAllCurrentByBookerId(bookerId, LocalDateTime.now());
                break;
            case PAST:
                bookings = bookingRepository.findAllPastByBookerId(bookerId, LocalDateTime.now());
                break;
            case FUTURE:
                bookings = bookingRepository.findAllFutureByBookerId(bookerId, LocalDateTime.now());
                break;
            case WAITING:
                bookings = bookingRepository.findAllWaitingByBookerId(bookerId);
                break;
            case REJECTED:
                bookings = bookingRepository.findAllRejectedByBookerId(bookerId);
                break;
            default: bookings = bookingRepository.findAllByBookerId(bookerId);
        }
        return bookings.stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
    }

    public List<ReturnBookingDto> getOwnerBookings(BookingState state, Long ownerId) { // сервис
        checkUserExist(ownerId);
        List<Booking> bookings;
        switch (state) {
            case CURRENT:
                bookings = bookingRepository.findAllCurrentByOwnerId(ownerId, LocalDateTime.now());
                break;
            case PAST:
                bookings = bookingRepository.findAllPastByOwnerId(ownerId, LocalDateTime.now());
                break;
            case FUTURE:
                bookings = bookingRepository.findAllFutureByOwnerId(ownerId, LocalDateTime.now());
                break;
            case WAITING:
                bookings = bookingRepository.findAllWaitingByOwnerId(ownerId);
                break;
            case REJECTED:
                bookings = bookingRepository.findAllRejectedByOwnerId(ownerId);
                break;
            default: bookings = bookingRepository.findAllByOwnerId(ownerId);
        }
        return bookings.stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
    }

    private User checkUserExist(Long ownerId) {
        log.info("Начата процедура проверки наличия в репозитории пользователя с id: {}", ownerId);
        Optional<User> userOptional = userRepository.findById(ownerId);
        if (userOptional.isEmpty()) {
            throw new UserExistException("Ошибка. Запрошенного пользователя в базе данных не существует");
        }
        return userOptional.orElseThrow();
    }

    private Item checkItemExist(Long itemId) {
        log.info("Начата процедура проверки наличия в репозитории предмета с id: {}", itemId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new ItemExistException("Ошибка. Запрошенного предмета в базе данных не существует");
        }
        return itemOptional.orElseThrow();
    }

    private void checkUserIsOwner(Long currentOwnerId, Long legalOwnerId) {
        log.info("Начата процедура проверки принадлежности предмета пользователю с id: {}", currentOwnerId);
        if (!(currentOwnerId.longValue() == legalOwnerId.longValue())) {
            throw new ItemWithWrongOwner("Ошибка. Обновить/удалить предмет может только владелец предмета");
        }
    }

    private Booking checkBookingExist(Long bookingId) {
        log.info("Начата процедура проверки наличия в репозитории брони с id: {}", bookingId);
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            throw new BookingExistException("Ошибка. Запрошенной брони в базе данных не существует");
        }
        return bookingOptional.orElseThrow();
    }
}
