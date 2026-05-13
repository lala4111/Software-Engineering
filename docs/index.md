

## 1. Introduction & Context

### 1.1 Scope

This document outlines the technical architecture for our university Course Management System. The primary objective for this development cycle is to deliver a **Minimum Viable Product (MVP)**. The MVP scope is strictly limited to essential operational features: **user authentication** (admin/student roles), **course catalog browsing**, and **core enrollment processing**.

### 1.2 Assumptions and Constraints

The architectural decisions for this project are driven by project constraints:

- **Time & Skill Constraints:** The project operates under a strict academic deadline. The development team utilizes pre-existing proficiency in core Java and JavaFX GUI development.

- **Technology Trade-offs:** Adopting a modern Web Architecture introduces an unacceptable schedule risk.

- **Deployment Assumption:** The system is designed as a standalone Desktop Application using JavaFX, assuming users will run the application locally with a direct JDBC connection to a local MySQL database.


---

## 2. Technology Strategy

### 2.1 Technology Stack

- **Programming Language:** Java SE

- **User Interface:** JavaFX

- **Database:** MySQL Relational Database

- **Database Connectivity:** JDBC API


### 2.2 Technical Rationale

- **Java & JavaFX:** Chosen to use existing team knowledge, allowing rapid prototyping of the UI without needing to configure web servers or REST API endpoints.

- **MySQL:** Selected because the domain entities (Students, Courses, Enrollments) exhibit strong relational dependencies. A relational database ensures data integrity through Foreign Keys and structured schema.

- **Raw JDBC:** Used instead of an Object-Relational Mapping (ORM) framework like Hibernate to minimize overhead and learning curves for this MVP.


---

## 3. System Architecture & Design

### 3.1 Architectural Layers

The application follows a classic **Three-Tier Architecture** implementation within a desktop environment, ensuring a clean Separation of Concerns:

- **Presentation Layer (`com.university/ui`):** Manages the graphical interface, event listeners, and user inputs using JavaFX.

- **Business Logic Layer (`com.university/service`):** Contains the core system rules and processing. It acts as the intermediary, receiving UI requests and orchestrating database operations.

- **Data Access Layer (`com.university/database` & `model`):** Handles object mapping and physical database connections via `DBConnection`.


### 3.2 Object-Oriented Design

The system utilizes Object-Oriented principles in the `model` package. While explicit Inheritance is minimized in favor of flat entity structures for the MVP, encapsulation is strictly maintained. The domain models (`Course`, `Enrollment`, `Person`) map directly to database tables. Standardized `ENUM` types are used in the Java code to strictly govern states (e.g., `Course.Level`, `Enrollment.PaymentStatus`), mirroring the database structure.

### 3.3 System Integration

Integration is achieved via a localized `DriverManager` connection string (`jdbc:mysql://localhost:3306/course_system`). The application acts as a direct database client, executing raw SQL queries and mapping `ResultSet` data back to Java objects.

---

## 4. Core Modules & API

### 4.1 EnrollmentService

The most critical transactional module in the system.

- **Enrollment Processing:** Handles the complex logic of registering a student to a course.

- **Data Aggregation:** Utilizes SQL `JOIN` statements to aggregate readable data across the `enrollment`, `course`, and `person` tables (e.g., mapping IDs to human-readable course titles and student names) for the UI tables.

### 4.2 CourseService

Manages the course catalog.

- **CRUD Operations:** Supports creating, reading, updating, and deleting courses.

- **Dynamic Filtering:** Implements complex retrieval logic based on user UI selections. It dynamically constructs SQL queries to filter by `category`, `fee`, and `level`.

### 4.3 LogInService, RegisterService & PersonService

These modules handle Identity and Access Management.

- **Registration:** Validates that emails are not already in use before inserting new records into the `person` table.

- **Authentication:** Checks plain-text password matches and extracts the user's ID and `Role` (`admin` or `user`) to dictate UI permissions in `MainApp`.

---

## 5. Database Schema

The database, `course_system`, is fully normalized to resolve many-to-many relationships and enforce data integrity.

- **`person` Table:** Acts as the central identity repository.
  - Enforces uniqueness on the `username` column.
  - Uses an `ENUM('user', 'admin')` for role-based access control.
- **`course` Table:** Stores the catalog.
  - `fee` is correctly typed as `DECIMAL` to prevent floating-point errors in financial data.
  - `level` is strictly constrained via `ENUM('beginner', 'intermediate', 'advanced')`
- **`enrollment` Table:** A junction table linking `person` and `course`.
  - Utilizes Foreign Keys (`id_student`, `id_course`) to ensure referential integrity.
  - Tracks business state via `payment_status` (`unpaid`, `paid`) and `enrollment_status` (`pending`, `enrolled`, `completed`, `dropped`).

---

## 6. Quality Attributes (Non-Functional Requirements)

### 6.1 Concurrency Control & Thread Safety

- **Design:** The `EnrollmentService` is architected to anticipate and handle race conditions when multiple users attempt to enroll in a course with limited seats. It addresses this via:
  - **Manual Transactions:** Disables auto-commit to group multiple SQL statements into a single atomic transaction.
  - **Pessimistic Locking:** Executes a `SELECT ... FOR UPDATE` query on the `course` table to lock the specific course row. This guarantees that if two threads check seat availability simultaneously, they are processed sequentially, preventing the over-allocation of seats.
  - **Rollbacks:** Safely aborts and reverts all database changes if a seat shortage or SQL exception occurs during the transaction.
- **Limitation:** Due to MVP timeline constraints, high-load concurrency and strict ACID isolation have not been validated through stress testing. The system is not certified to support the target of 500 concurrent users at this phase.
### 6.2 Security & Compliance
- **Authentication & Authorization:** The system successfully implements Role-Based Access Control (RBAC), ensuring administrative dashboards and functions are strictly isolated from standard student accounts. Unauthenticated users are forced to log in before executing transactions.
- **Limitation (Accepted Risk):** To expedite development, all data (including user passwords and personal information) is stored in plain text. The MVP does not currently implement cryptographic hashing (e.g., BCrypt) or GDPR-compliant automated data deletion protocols.

### 6.3 System Responsiveness, Usability & Modifiability

- **Usability:** The JavaFX interface is designed for immediate usability, requiring no specialized training to navigate the catalog or enroll. The UI provides immediate visual feedback via JavaFX `Alert` dialogs for successes and errors.
- **State Management & UI Synchronization:** The architecture successfully implements dynamic UI updates. When backend database transactions occur (e.g., decreasing seat counts upon successful enrollment, or an admin adding/deleting a course), the JavaFX frontend automatically refreshes to reflect these state changes in real-time, providing a seamless experience without requiring an application restart.
- **Modifiability:** The layered architecture ensures that if the system later transitions to a Web API, the `model` and `service` classes can be reused with minimal modification.