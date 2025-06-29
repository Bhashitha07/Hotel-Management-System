# 🏨 Hotel Room Reservation System

A full-featured Hotel Room Reservation System built using **Java**, **JSP**, and **Servlets** following **OOP principles** and **file-based persistence** (TXT). The system includes modules for managing users, rooms, reservations, payments, staff, and guest reviews.

---

## 📁 Project Structure

### ✅ Component 01: User Management
**Description:** Handles guest and staff account management, including registration, login, and profile updates.

#### 🔧 CRUD Operations:
- **Create:** Register guests and staff in `users.txt`
- **Read:** Search users by username, ID, or role
- **Update:** Modify user info (contact, password)
- **Delete:** Remove user accounts

#### 💻 UI Components:
- Guest Registration Page
- Staff Registration Page (Admin-Only)
- User Login Page
- Profile Update Page
- User List Page (Admin View)

#### 🧠 OOP Concepts:
- **Encapsulation:** Secure user data in `User` class
- **Inheritance:** `Guest` and `Staff` inherit from `User`
- **Polymorphism:** Different login behavior for user types

---

### ✅ Component 02: Room Management
**Description:** Manages hotel rooms including their addition, update, and deletion.

#### 🔧 CRUD Operations:
- **Create:** Add rooms to `rooms.txt`
- **Read:** Search by type, price, or availability
- **Update:** Modify room details (price, maintenance status)
- **Delete:** Remove rooms from inventory

#### 💻 UI Components:
- Room Addition Form (Admin-Only)
- Room Search Page
- Room Edit Page (Admin-Only)
- Room Listing Page

#### 🧠 OOP Concepts:
- **Encapsulation:** `Room` class stores room info
- **Inheritance:** `StandardRoom` and `DeluxeRoom` inherit `Room`
- **Polymorphism:** Vary pricing based on room type

---

### ✅ Component 03: Reservation Management
**Description:** Supports room booking, cancellation, and check-in/check-out.

#### 🔧 CRUD Operations:
- **Create:** Book reservation in `reservations.txt`
- **Read:** View by user/date
- **Update:** Modify reservation (extend, change room)
- **Delete:** Cancel reservation

#### 💻 UI Components:
- Reservation Booking Page
- Reservation List Page (Guest/Staff Views)
- Edit/Cancel Reservation Page

#### 🧠 OOP Concepts:
- **Encapsulation:** `Reservation` class for records
- **Abstraction:** Availability checks before booking
- **Polymorphism:** Custom cancellation policies

---

### ✅ Component 04: Staff Management
**Description:** Controls staff registration, roles, and admin permissions.

#### 🔧 CRUD Operations:
- **Create:** Add staff to `staff.txt`
- **Read:** View staff and roles
- **Update:** Change roles or permissions
- **Delete:** Remove staff members

#### 💻 UI Components:
- Staff Dashboard
- Staff Registration Page (Admin-Only)
- Staff Management Panel

#### 🧠 OOP Concepts:
- **Encapsulation:** `Staff` class protects data
- **Inheritance:** Roles like `Receptionist`/`Manager` extend `Staff`
- **Abstraction:** Admin-only role control

---

### ✅ Component 05: Payment Management
**Description:** Manages payments, invoices, and refunds.

#### 🔧 CRUD Operations:
- **Create:** Save payments to `payments.txt`
- **Read:** View by guest/reservation
- **Update:** Refunds or update statuses
- **Delete:** Archive processed payments

#### 💻 UI Components:
- Payment Processing Page
- Invoice Generation Page
- Payment History Page

#### 🧠 OOP Concepts:
- **Encapsulation:** `Payment` class handles transactions
- **Polymorphism:** Multiple payment methods
- **Abstraction:** Tax and discount calculations

---

### ✅ Component 06: Feedback & Review Management
**Description:** Guests submit reviews and feedback; admins moderate them.

#### 🔧 CRUD Operations:
- **Create:** Add reviews to `reviews.txt`
- **Read:** Display reviews by room/hotel
- **Update:** Edit feedback (user)
- **Delete:** Admin removes inappropriate reviews

#### 💻 UI Components:
- Review Submission Page
- Public Reviews Display
- Admin Moderation Panel

#### 🧠 OOP Concepts:
- **Encapsulation:** `Review` class
- **Inheritance:** `PublicReview`, `VerifiedReview` extend `Review`
- **Polymorphism:** Different UI rendering for guests/admins

---

## 🧪 Technologies Used
- Java
- JSP / Servlets
- HTML5 / CSS3 (Bootstrap)
- File I/O (`.txt` and `.json`)
- Jakarta EE
- Object-Oriented Programming (OOP)

---

## 📌 How to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/hotel-reservation-system.git
