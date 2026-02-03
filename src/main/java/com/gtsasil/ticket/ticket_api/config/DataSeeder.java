package com.gtsasil.ticket.ticket_api.config;

import com.gtsasil.ticket.ticket_api.enums.SeatStatus;
import com.gtsasil.ticket.ticket_api.model.Seat;
import com.gtsasil.ticket.ticket_api.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final SeatRepository seatRepository;

    @Override
    public void run(String... args) throws Exception {
        if (seatRepository.count() == 0) {
            System.out.println("🌱 Seeding database with seats...");

            for (int i = 1; i <= 50; i++) {
                Seat seat = new Seat();

                seat.setCode("A" + i); 
                seat.setStatus(SeatStatus.AVAILABLE);
                
                seatRepository.save(seat);
            }
            
            System.out.println("✅ 50 Seats created!");
        }
    }
}