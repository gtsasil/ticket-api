package com.gtsasil.ticket.ticket_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TicketConcurrencySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketConcurrencySystemApplication.class, args);
	}

}
