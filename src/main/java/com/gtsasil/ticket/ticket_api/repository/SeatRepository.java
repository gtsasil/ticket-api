package com.gtsasil.ticket.ticket_api.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gtsasil.ticket.ticket_api.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    
    @Modifying
    @Query("UPDATE Seat a SET a.status = 'RESERVED', a.reservedBy = :reservedBy, a.expiresAt = :expiresAt WHERE a.id = :id AND a.status = 'AVAILABLE'")
    int reserveSeat(@Param("id") Long id, @Param("reservedBy") Long reservedBy, @Param("expiresAt") java.time.LocalDateTime expiresAt);

    @Modifying
    @Query("UPDATE Seat s SET s.status = 'AVAILABLE', s.reservedBy = NULL, s.expiresAt = NULL WHERE s.status = 'RESERVED' AND s.expiresAt < :now")
    int cancelExpiredReservations(@Param("now") LocalDateTime now);

    @Modifying
    @Query("UPDATE Seat s SET s.status = 'SOLD', s.expiresAt = NULL WHERE s.id = :seatId AND s.reservedBy = :userId AND s.status = 'RESERVED'")
    int finalizePurchase(@Param("seatId") Long seatId, @Param("userId") Long userId);
}
