package com.gtsasil.ticket.ticket_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.gtsasil.ticket.ticket_api.enums.SeatStatus;
import com.gtsasil.ticket.ticket_api.model.Seat;
import com.gtsasil.ticket.ticket_api.repository.SeatRepository;
import com.gtsasil.ticket.ticket_api.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TicketConcurrencySystemApplicationTests {
@Autowired
    private SeatService seatService;

    @Autowired
    private SeatRepository seatRepository;

    @Test
    public void testRaceCondition() throws InterruptedException {
        Seat seat = seatRepository.findById(1L).orElseThrow();
        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setReservedBy(null);
        seatRepository.save(seat);

        int numberOfThreads = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        System.out.println("🚀 Starting attack of 100 threads to Seat 1...");

        for (int i = 0; i < numberOfThreads; i++) {
            long userId = i + 1000;
            
            executor.submit(() -> {
                try {
                    seatService.reserveSeat(1L, userId);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("🏁 Final Result:");
        System.out.println("✅ Successes: " + successCount.get());
        System.out.println("❌ Failures (Conflicts): " + failCount.get());

        assertEquals(1, successCount.get(), "Should have exactly 1 successful reservation!");
        assertEquals(99, failCount.get(), "Should have 99 conflicts!");
    }

}
