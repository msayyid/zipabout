# ZipAbout – Vehicle Rental System (Sprint 3)

## Module Information
- **Module:** Software Development 3 (CMP020N201)
- **Programme:** BSc Computer Science
- **Assessment:** Sprint 3
- **Student:** Mukhammadsaiid Norbaev  
- **Student ID:** NOR22597897  
- **Submission Date:** 06 January 2026

---

## Project Overview

**ZipAbout** is a Java-based vehicle rental system for shared lightweight electric vehicles such as bikes and scooters.  
The system has been developed incrementally using a sprint-based approach.

- **Sprint 1:** Object-oriented domain model design  
- **Sprint 2:** Booking logic and business rules via a service layer  
- **Sprint 3:** JavaFX graphical user interface with role-based access control  

Sprint 3 focuses on delivering an **event-driven GUI** that integrates with the existing domain and service layers without duplicating business logic.

---

## Key Features (Sprint 3)

### User (Customer) Functionality
- View all vehicles and their availability status
- Select vehicles and view detailed information
- Book available vehicles
- Release owned vehicles
- View active and past rentals
- Receive clear visual feedback for all actions
- Enforced restrictions:
  - One active rental per user
  - Cannot book already-booked vehicles
  - Cannot release vehicles owned by another user

### Admin Functionality (Role-Based Access)
- Access admin-only dashboard
- Add, view, and remove users
- Add and view vehicles
- View vehicle rental status and rental history
- Confirmation dialogs for destructive actions
- Enforced restrictions:
  - Users with active rentals cannot be deleted

### System Behaviour
- Real-time UI updates when data changes
- Business rules enforced via the existing service layer
- No business logic inside JavaFX controllers

---

## Architecture Overview

The system follows a **layered architecture**:

- **Domain Layer:**  
  Vehicles, users, rentals, and equipment modeled using OOP principles

- **Service Layer:**  
  Handles booking logic, validation, and business rules

- **UI Layer (JavaFX):**  
  Event-driven controllers responsible for presentation and interaction only

This ensures **separation of concerns**, maintainability, and reuse of existing logic.

---

## Programming Paradigms Used

- **Object-Oriented Programming**
  - Abstraction, inheritance, polymorphism
  - Vehicle hierarchies and user–rental relationships

- **Event-Driven Programming**
  - JavaFX UI interactions (buttons, selections, dialogs)

- **Functional Programming**
  - Java Streams for filtering vehicles and rentals
  - Cleaner and more readable collection processing

---

## Technologies Used

- Java
- JavaFX
- Java Streams
- Object-Oriented Design
- Event-Driven GUI Architecture

---

## How to Run

1. Open the project in a Java-compatible IDE (e.g. IntelliJ IDEA)
2. Ensure JavaFX is correctly configured
3. Run the main application class
4. Log in using an existing user or admin account

---

## Notes

- The class diagram and core domain design are reused from Sprint 2
- All business rules are enforced consistently through the service layer
- Admin controls are hidden from non-admin users at runtime

---

## AI Usage Declaration

Artificial Intelligence tools were used **only as a supporting aid** for:
- Clarifying concepts
- Improving written explanations
- Refining documentation structure

All design decisions, implementation, and logic were fully produced and understood by the student.

---

## Author

**Mukhammadsaiid Norbaev**  
BSc Computer Science  
University of Roehampton
