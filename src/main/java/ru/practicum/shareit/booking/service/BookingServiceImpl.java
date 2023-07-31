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
import ru.practicum.shareit.exceptions.ItemWithWrongOwner;
import ru.practicum.shareit.exceptions.ItemWithoutOwnerException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepositoryImpl bookingRepository;
    private final UserRepositoryImpl userRepository;
    private final ItemRepositoryImpl itemRepository;

    public ReturnBookingDto addBooking(ReceivedBookingDto bookingDto, Long userId) {
        Item item = checkItemExist(bookingDto.getItemId());
        User user = checkUserExist(userId);
        Booking booking = BookingMapper.toBookingFromReceivedDto(bookingDto, item, user);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    public ReturnBookingDto setBookingStatus(Long bookingId, Boolean status, Long ownerId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        checkUserIsOwner(ownerId, booking.getItem().getOwner().getId());
        if (status) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    public ReturnBookingDto getBooking(Long bookingId) {
        return BookingMapper.toBookingDto(bookingRepository.findById(bookingId).orElseThrow());
    }

    @Transactional
    public List<ReturnBookingDto> getOwnerBookings(BookingState state, Long ownerId) {
        List<Booking> bookings;
        switch (state) {
            case ALL:
                bookings = bookingRepository.findAllByBookerId(ownerId);
                break;
            case CURRENT:
                bookings = bookingRepository.findAllCurrentByBookerId(ownerId);
                break;
            case PAST:
                bookings = bookingRepository.findAllPastByBookerId(ownerId);
                break;
            case FUTURE:
                bookings = bookingRepository.findAllFutureByBookerId(ownerId);
                break;
            case WAITING:
                bookings = bookingRepository.findAllWaitingByBookerId(ownerId);
                break;
            case REJECTED:
                bookings = bookingRepository.findAllRejectedByBookerId(ownerId);
                break;
            default: bookings = List.of();
        }
        return bookings.stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ReturnBookingDto> getOwnerItemsBookings(BookingState state, Long ownerId) {
        List<Booking> bookings;
        switch (state) {
            case ALL:
                bookings = bookingRepository.findAllByOwnerId(ownerId);
                break;
            case CURRENT:
                bookings = bookingRepository.findAllCurrentByOwnerId(ownerId);
                break;
            case PAST:
                bookings = bookingRepository.findAllPastByOwnerId(ownerId);
                break;
            case FUTURE:
                bookings = bookingRepository.findAllFutureByOwnerId(ownerId);
                break;
            case WAITING:
                bookings = bookingRepository.findAllWaitingByOwnerId(ownerId);
                break;
            case REJECTED:
                bookings = bookingRepository.findAllRejectedByOwnerId(ownerId);
                break;
            default: bookings = List.of();
        }
        return bookings.stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
    }

    private User checkUserExist(Long ownerId) {
        log.info("Начата процедура проверки наличия в репозитории пользователя с id: {}", ownerId);
        Optional<User> userOptional = userRepository.findById(ownerId);
        if (userOptional.isEmpty()) {
            throw new ItemWithoutOwnerException("Ошибка. " +
                    "Создать/обновить/удалить предмет может только зарегистрированный в приложении пользователь");
        }
        return userOptional.orElseThrow();
    }

    private Item checkItemExist(Long itemId) {
        log.info("Начата процедура проверки наличия в репозитории предмета с id: {}", itemId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new ItemWithoutOwnerException("Ошибка. " +
                    "Создать/обновить/удалить предмет может только зарегистрированный в приложении пользователь");
        }
        return itemOptional.orElseThrow();
    }

    private void checkUserIsOwner(Long currentOwnerId, Long legalOwnerId) {
        log.info("Начата процедура проверки принадлежности предмета пользователю с id: {}", currentOwnerId);
        if (!currentOwnerId.equals(legalOwnerId)) {
            throw new ItemWithWrongOwner("Ошибка. Обновить/удалить предмет может только владелец предмета");
        }
    }
}
