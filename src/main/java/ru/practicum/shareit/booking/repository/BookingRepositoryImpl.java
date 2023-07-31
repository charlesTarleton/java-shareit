package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepositoryImpl extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllByBookerId(Long bookerId); // Booker ALL

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.start < now() AND b.end > now() AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllCurrentByBookerId(Long bookerId); // Booker CURRENT

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.end < now() AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllPastByBookerId(Long bookerId); // Booker PAST

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.start > now() AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllFutureByBookerId(Long bookerId); // Booker FUTURE

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.status = WAITING AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllWaitingByBookerId(Long bookerId); // Booker WAITING

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.status = REJECTED AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllRejectedByBookerId(Long bookerId); // Booker REJECTED

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.item.owner = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllByOwnerId(Long ownerId); // Owner ALL

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.start < now() AND b.end > now() b.item.owner = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllCurrentByOwnerId(Long ownerId); // Owner CURRENT

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.end < now() AND b.item.owner = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllPastByOwnerId(Long ownerId); // Owner PAST

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.start > now() AND b.item.owner = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllFutureByOwnerId(Long ownerId); // Owner FUTURE

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.status = WAITING AND b.item.owner = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllWaitingByOwnerId(Long ownerId); // Owner WAITING

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "JOIN FETCH b.item " +
            "WHERE b.status = REJECTED AND b.item.owner = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllRejectedByOwnerId(Long ownerId); // Owner REJECTED
}
