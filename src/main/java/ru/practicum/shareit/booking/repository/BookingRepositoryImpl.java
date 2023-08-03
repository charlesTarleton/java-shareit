package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepositoryImpl extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "WHERE b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllByBookerId(Long bookerId); // Booker ALL

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "WHERE b.start < ?2 AND b.end > ?2 AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllCurrentByBookerId(Long bookerId, LocalDateTime currentTime); // Booker CURRENT

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "WHERE b.end < ?2 AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllPastByBookerId(Long bookerId, LocalDateTime currentTime); // Booker PAST

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "WHERE b.start > ?2 AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllFutureByBookerId(Long bookerId, LocalDateTime currentTime); // Booker FUTURE

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "WHERE b.status = waiting AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllWaitingByBookerId(Long bookerId); // Booker WAITING

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "WHERE b.status = rejected AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllRejectedByBookerId(Long bookerId); // Booker REJECTED

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.item " +
            "WHERE b.item.owner.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllByOwnerId(Long ownerId); // Owner ALL

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.item " +
            "WHERE b.start < ?2 AND b.end > ?2 AND b.item.owner.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllCurrentByOwnerId(Long ownerId, LocalDateTime currentTime); // Owner CURRENT

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.item " +
            "WHERE b.end < ?2 AND b.item.owner.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllPastByOwnerId(Long ownerId, LocalDateTime currentTime); // Owner PAST

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.item " +
            "WHERE b.start > ?2 AND b.item.owner.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllFutureByOwnerId(Long ownerId, LocalDateTime currentTime); // Owner FUTURE

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.item " +
            "WHERE b.status = waiting AND b.item.owner.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllWaitingByOwnerId(Long ownerId); // Owner WAITING

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.item " +
            "WHERE b.status = rejected AND b.item.owner.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllRejectedByOwnerId(Long ownerId); // Owner REJECTED

    BookingItemDto findFirstByEndBeforeAndItemIdOrderByEndDesc(LocalDateTime currentTime, Long itemId);

    BookingItemDto findFirstByStartAfterAndItemIdOrderByStartAsc(LocalDateTime currentTime, Long itemId);
}
