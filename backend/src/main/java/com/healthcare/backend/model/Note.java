package com.healthcare.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private TriageCase triageCase;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private String noteText;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Note() {}

    public Long getNoteId() { return noteId; }
    public void setNoteId(Long noteId) { this.noteId = noteId; }

    public TriageCase getTriageCase() { return triageCase; }
    public void setTriageCase(TriageCase triageCase) { this.triageCase = triageCase; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public String getNoteText() { return noteText; }
    public void setNoteText(String noteText) { this.noteText = noteText; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}