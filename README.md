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
