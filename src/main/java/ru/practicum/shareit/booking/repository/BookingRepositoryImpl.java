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
            "WHERE b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllByBookerId(Long bookerId); // Booker ALL

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "WHERE b.start < NOW() AND b.end > NOW() AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllCurrentByBookerId(Long bookerId); // Booker CURRENT

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "WHERE b.end < NOW() AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllPastByBookerId(Long bookerId); // Booker PAST

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.booker " +
            "WHERE b.start > NOW() AND b.booker.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllFutureByBookerId(Long bookerId); // Booker FUTURE

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
            "WHERE b.start < NOW() AND b.end > NOW() AND b.item.owner.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllCurrentByOwnerId(Long ownerId); // Owner CURRENT

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.item " +
            "WHERE b.end < NOW() AND b.item.owner.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllPastByOwnerId(Long ownerId); // Owner PAST

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN FETCH b.item " +
            "WHERE b.start > NOW() AND b.item.owner.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllFutureByOwnerId(Long ownerId); // Owner FUTURE

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
}
