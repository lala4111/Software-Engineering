<div align="center">

  <h1>Course Management & Enrollment System</h1>

  <p>
    A Java-based desktop application for university course registration.
  </p>

</div>

<br />

<!-- Table of Contents -->
# Table of Contents

- [About the Project](#about-the-project)
    * [Tech](#tech)
    * [Features](#features)
- [Database Architecture](#database-architecture)
- [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Database Setup](#database-setup)
    * [Installation & Configuration](#installation--configuration)
- [Usage](#usage)


<!-- About the Project -->
## About the Project

This project is a local desktop application designed to regulate the course enrollment process for both students (Users) and university administrators (Admins). 

<!-- Tech -->
### Tech

<details>
  <summary>Application</summary>
  <ul>
    <li><a href="https://www.java.com/">Java (JDK 8+)</a></li>
    <li><a href="https://openjfx.io/">JavaFX</a> (GUI Framework)</li>
    <li>JDBC (Java Database Connectivity)</li>
  </ul>
</details>

<details>
<summary>Database</summary>
  <ul>
    <li><a href="https://www.mysql.com/">MySQL</a> </li>
  </ul>
</details>

<!-- Features -->
### Features

**For Users (Students):**
* **Browse Courses**: View available courses, including details like schedule, credits, fees, and available seats.
* **Enrollment**: Safely enroll in courses. The system prevents overbooking when seats are full.

**For Administrators:**
* **Course Management**: Create, edit and delete courses.
* **Registration Management**: Update and modify the status of student enrollments (e.g., payment and enrollment status).
* **Dashboard**: Monitor all enrollments.


<!-- Database Architecture -->
## Database Architecture

The system relies on a relational model consisting of three main entities:
1. `person`: roles (`user` or `admin`)
2. `course`
3. `enrollment`

<!-- Getting Started -->
## Getting Started

Follow these instructions to set up our app locally!

<!-- Prerequisites -->
### Prerequisites

Ensure you have the following installed on your local machine:
* Java Development Kit (JDK)
* MySQL Server
* Any Java IDE
* ???

<!-- Database Setup -->
### Database Setup

1. Open your MySQL client or terminal.
2. Execute the provided SQL script to initialize the database and insert dummy data:
```sql
DROP DATABASE IF EXISTS course_system;
...
TBD