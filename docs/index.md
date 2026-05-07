# Technical Architecture

*Note: TBD
## 1. Design Principles
The system uses a three-tier architecture (JavaFX, Java Services, MySQL) and follows Object-Oriented principles:
*   Shared behaviors are designed to be managed by a **Superclass**.
*   Specific modules act as a **Subclass**, applying **Inheritance** to extend core functionalities cleanly.

## 2. Core Services
*   **`EnrollmentService`**: Manages registrations. 
*   **`CourseService`**: Handles course creation and data retrieval.
*   **`PersonService` & `LogInService`**: Manages Role-Based Access Control (Admin vs. User).

## 3. Database Schema
The database (`course_system`) relies on three core tables:
1.  `person`
2.  `course`
3.  `enrollment`