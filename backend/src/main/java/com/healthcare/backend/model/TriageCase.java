package com.healthcare.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "triage_cases")
public class TriageCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id")
    private Long caseId;

    @Column(name = "case_date", nullable = false, updatable = false)
    private LocalDateTime caseDate;

    @Column(name = "chief_complaint", nullable = false, length = 1000)
    private String chiefComplaint;

    @Column(name = "recommendation", length = 1000)
    private String recommendation;

    @Column(name = "red_flag_found", nullable = false)
    private boolean redFlagFound;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private TriageStatus status = TriageStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "urgency_level", nullable = false, length = 30)
    private UrgencyLevel urgencyLevel;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "assigned_doctor_id")
    private Doctor assignedDoctor;

    public TriageCase() {
    }

    public TriageCase(
            String chiefComplaint,
            String recommendation,
            boolean redFlagFound,
            TriageStatus status,
            UrgencyLevel urgencyLevel,
            Patient patient,
            Doctor assignedDoctor) {

        this.chiefComplaint = chiefComplaint;
        this.recommendation = recommendation;
        this.redFlagFound = redFlagFound;
        this.status = status;
        this.urgencyLevel = urgencyLevel;
        this.patient = patient;
        this.assignedDoctor = assignedDoctor;
    }

    @PrePersist
    public void setDefaultValues() {

        if (caseDate == null) {
            caseDate = LocalDateTime.now();
        }

        if (status == null) {
            status = TriageStatus.PENDING;
        }
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public LocalDateTime getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(LocalDateTime caseDate) {
        this.caseDate = caseDate;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public boolean isRedFlagFound() {
        return redFlagFound;
    }

    public void setRedFlagFound(boolean redFlagFound) {
        this.redFlagFound = redFlagFound;
    }

    public TriageStatus getStatus() {
        return status;
    }

    public void setStatus(TriageStatus status) {
        this.status = status;
    }

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }
}