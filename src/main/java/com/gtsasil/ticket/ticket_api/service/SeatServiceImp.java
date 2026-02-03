package com.gtsasil.ticket.ticket_api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtsasil.ticket.ticket_api.exception.SeatAlreadyReservedException;
import com.gtsasil.ticket.ticket_api.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatServiceImp implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public void reserveSeat(Long seatId, Long userId) {
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);

        int updatedRows = seatRepository.reserveSeat(seatId, userId, expiresAt);

        if (updatedRows == 0) {
            throw new SeatAlreadyReservedException("Seat " + seatId + " is already reserved or not available.");
        }
    }

    @Override
    @Transactional
    public String finalizePurchase(Long seatId, Long userId) {
        int updatedRows = seatRepository.finalizePurchase(seatId, userId);

        if (updatedRows == 0) {
            throw new SeatAlreadyReservedException("Reservation for seat " + seatId + " is expired or invalid.");
        }

        return "TICKET-" + seatId + "-" + userId + "-" + System.currentTimeMillis();
    }
    
    
}
