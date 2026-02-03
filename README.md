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

```

* If the Database returns **1 row updated**: The seat is yours. ✅
* If the Database returns **0 rows updated**: Someone beat you to it. ❌ -> **Throws 409 Conflict**.

---

## 🛠️ Tech Stack

* **Language:** Java 21 (LTS)
* **Framework:** Spring Boot 3 (Web, Data JPA)
* **Database:** H2 Database (In-Memory for rapid prototyping)
* **Tools:** Maven, Lombok, JUnit 5.

---

## 🏃‍♂️ Getting Started

### Prerequisites

* Java 21 installed.
* Maven installed (or use the included `mvnw` wrapper).

### Installation

1. **Clone the repository:**
```bash
git clone [https://github.com/YOUR-USERNAME/ticket-concurrency-system.git](https://github.com/YOUR-USERNAME/ticket-concurrency-system.git)
cd ticket-concurrency-system

```


2. **Run the application:**
```bash
./mvnw spring-boot:run

```


3. **Access the Database (H2 Console):**
* URL: `http://localhost:8080/h2-console`
* JDBC URL: `jdbc:h2:mem:ticketdb`
* User: `sa` / Password: (empty)



---

## 🔌 API Endpoints

### 1. Reserve a Seat

Locks a seat for 5 minutes.

```bash
POST /api/seats/{seatId}/reserve?userId={userId}

```

* **Success (200):** "Seat reserved successfully!"
* **Failure (409):** "Seat is already reserved..."

### 2. Confirm Purchase

Finalizes the booking if the reservation is still valid.

```bash
POST /api/seats/{seatId}/pay?userId={userId}

```

* **Success (200):** Returns a Ticket Token.

---

## 🧪 Testing Concurrency

To prove the system works, run the included Integration Test (`ConcurrencyTest.java`).

**Scenario:**

* **100 Threads** try to book Seat #1 at the exact same time.
* **Expected Result:** Exactly **1 Success** and **99 Failures**.

To run tests via terminal:

```bash
./mvnw test

```

---

## 🔮 Future Improvements

* [ ] Add Swagger/OpenAPI documentation.
* [ ] Dockerize the application.
* [ ] Migrate from H2 to PostgreSQL for production.

---

## 📝 License

This project is licensed under the MIT License.

