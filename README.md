# 🎟️ Ticket Concurrency System (MVP)

> A High-Performance Ticket Booking System built with **Spring Boot 3** and **Java 21**, designed to handle **High Concurrency** and prevent **Overbooking** (Race Conditions).

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-green)
![Database](https://img.shields.io/badge/Database-H2-blue)

## 📖 About the Project

This project simulates a real-world ticketing scenario (like a concert or cinema booking) where thousands of users try to buy the same seat simultaneously.

The core challenge addressed here is the **Race Condition**: ensuring that a seat is never sold to two different people, even if they click "Buy" at the exact same millisecond.

### 🚀 Key Features
* **Concurrency Control:** Uses Atomic Database Updates (Optimistic Locking strategy) to prevent overbooking.
* **Reservation Lifecycle:**
    1.  **Reserve:** Locks the seat temporarily.
    2.  **Expire:** A background scheduler cleans up unpaid reservations automatically.
    3.  **Purchase:** Converts a valid reservation into a final sale.
* **Automated Testing:** Includes a rigorous integration test simulating **100 concurrent threads** attacking a single seat to prove system stability.

---

## ⚙️ How It Works (The "Secret Sauce")

Instead of using heavy Java-level locks (`synchronized`), this project pushes the concurrency control to the Database level for maximum performance.

**The Magic Query:**
```sql
UPDATE Seat s 
SET s.status = 'RESERVED', s.reservedBy = :userId, s.expiresAt = :expireTime 
WHERE s.id = :seatId 
AND s.status = 'AVAILABLE' -- The Guard Clause
