package com.gtsasil.ticket.ticket_api.model;

import java.time.LocalDateTime;

import com.gtsasil.ticket.ticket_api.enums.SeatStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "seats")
public class Seat {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private Long reservedBy;

    private LocalDateTime expiresAt;


}
