package com.gtsasil.ticket.ticket_api.service;


public interface SeatService {
    
    void reserveSeat(Long seatId, Long userId);
    String finalizePurchase(Long seatId, Long userId);
}
