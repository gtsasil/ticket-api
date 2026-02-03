package com.gtsasil.ticket.ticket_api.scheduler;

import com.gtsasil.ticket.ticket_api.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SeatCleanupScheduler {
 private final SeatRepository seatRepository;

    // 10 secs just for testing purposes
    @Scheduled(fixedRate = 10000) 
    @Transactional
    public void checkExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        
        int expiredCount = seatRepository.cancelExpiredReservations(now);
        
        if (expiredCount > 0) {
            System.out.println("🧹 The janitor has passed: " + expiredCount + " expired reservations were canceled.");
        }
    }
}