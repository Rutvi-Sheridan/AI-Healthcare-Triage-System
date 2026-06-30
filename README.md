
# AI Healthcare Triage System - Backend

This repository contains the **Spring Boot backend** for the AI Healthcare Triage System.

---

# Tech Stack

* Java 21
* Spring Boot
* Maven
* REST API
* MySQL (or H2 during development)
* Git & GitHub

---

# Prerequisites

Before running the project, make sure you have installed:

* Java JDK 21 (or the version specified in `pom.xml`)
* Apache Maven (optional if using Maven Wrapper)
* Git
* VS Code / IntelliJ IDEA / Eclipse

Verify your installation:

```bash
java -version
mvn -version
git --version
```

---

# Clone the Repository

```bash
git clone https://github.com/Rutvi-Sheridan/AI-Healthcare-Triage-System.git
```

Go into the project folder:

```bash
cd AI-Healthcare-Triage-System
```

Navigate to the backend:

```bash
cd backend
```

---

# Open the Project

Open the backend folder in your preferred IDE.

For VS Code:

```bash
code .
```

---

# Configure the Database

Open:

```
src/main/resources/application.properties
```

Update the database credentials if required.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/healthcare_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
```

---

# Build the Project

Using Maven:

```bash
mvn clean install
```

Or using Maven Wrapper (recommended):

Windows

```bash
mvnw.cmd clean install
```

Mac/Linux

```bash
./mvnw clean install
```

---

# Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Using Maven Wrapper

Windows

```bash
mvnw.cmd spring-boot:run
```

Mac/Linux

```bash
./mvnw spring-boot:run
```

---

# Backend URL

After the application starts successfully:

```
http://localhost:8080
```

---

# Run Tests

```bash
mvn test
```

---

# Git Workflow

## Pull the latest changes

```bash
git pull origin main
```

## Create your own branch

Replace `your-name` with your name.

```bash
git checkout -b your-name-backend
```

Example:

```bash
git checkout -b ayushi-backend
```

Push the branch for the first time:

```bash
git push -u origin ayushi-backend
```

---

# Save Your Changes

Stage files:

```bash
git add .
```

Commit:

```bash
git commit -m "Describe your changes"
```

Push:

```bash
git push
```

---

# Merge Workflow

Once your feature is completed:

1. Push your branch.
2. Create a Pull Request on GitHub.
3. Get approval from the team.
4. Merge into the `main` branch.

Do **not** work directly on the `main` branch.

---

# Useful Maven Commands

Clean project:

```bash
mvn clean
```

Compile project:

```bash
mvn compile
```

Package project:

```bash
mvn package
```

Install dependencies:

```bash
mvn install
```

Run tests:

```bash
mvn test
```

---

# Project Structure

```
backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │
│   └── test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

---

# Notes

* Always pull the latest changes before starting work.
* Create a new branch for each feature or task.
* Do not commit the `target/` directory.
* Do not commit IDE-specific files such as `.idea/` or `.vscode/`.
* Keep commit messages short and meaningful.
* Test your code before creating a Pull Request.

---


=======
# AI-Powered Healthcare Triage and Voice Intake System

## Overview

The AI-Powered Healthcare Triage and Voice Intake System is a full-stack
web application designed to help patients submit symptoms using text or
voice input and receive an AI-assisted urgency recommendation. The
system also provides clinic staff with a dashboard to review,
prioritize, and manage patient cases efficiently.

> Important: This system is a prototype and does NOT provide medical
> diagnosis or treatment advice.

------------------------------------------------------------------------

## Features

-   Patient registration and authentication
-   Role-based access (Patient, Staff, Provider, Admin)
-   Symptom intake via text input
-   Voice-to-text symptom entry (Web Speech API / fallback typing)
-   AI-assisted urgency classification:
    -   Self-care
    -   Family doctor
    -   Walk-in clinic
    -   Urgent care
    -   Emergency warning
-   Follow-up questions based on symptoms
-   Patient case history tracking
-   Appointment request system
-   Clinic staff dashboard for triage management
-   Provider view for case summaries and notes
-   Admin analytics dashboard (non-identifying data)

------------------------------------------------------------------------

## Tech Stack

### Frontend

-   React
-   HTML, CSS, JavaScript

### Backend

-   Spring Boot (Java)
-   Spring Security (JWT/session authentication)
-   REST APIs

### Database

-   MySQL

### AI/NLP Layer

-   Rule-based symptom classifier
-   Keyword + red-flag detection logic

### Speech-to-Text

-   Web Speech API (primary)
-   Google Cloud Speech-to-Text (optional fallback)

------------------------------------------------------------------------

## Team Members

-   Ayushi Patel -- Backend Development (Spring Boot, APIs, Database)
-   Rutvi Panchal -- Frontend Development (React UI, Integration)
-   Neet Patel -- Integration & Testing

------------------------------------------------------------------------

## Future Improvements

-   Advanced AI model for symptom classification
-   Integration with real clinic scheduling systems
-   Mobile application (Android/iOS)
-   Multilingual support
-   Improved voice recognition accuracy

------------------------------------------------------------------------

## License

This project is developed for academic capstone purposes only.

