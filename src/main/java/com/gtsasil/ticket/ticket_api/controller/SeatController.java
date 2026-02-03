package com.gtsasil.ticket.ticket_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.gtsasil.ticket.ticket_api.service.SeatService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
    
    private final SeatService  seatService;

    @PostMapping("/{seatId}/reserve")
    public ResponseEntity<String> reserveSeat(
            @PathVariable Long seatId, 
            @RequestParam Long userId) {

        seatService.reserveSeat(seatId, userId);
        
        return ResponseEntity.ok("Seat reserved successfully!");
    }
    
    @PostMapping("/{seatId}/pay")
    public ResponseEntity<String> payForSeat(@PathVariable Long seatId, @RequestParam Long userId) {
        String ticketToken = seatService.finalizePurchase(seatId, userId);
        return ResponseEntity.ok("Purchase confirmed! Your Ticket: " + ticketToken);
    }
}
