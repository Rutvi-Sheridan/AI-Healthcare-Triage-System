package com.healthcare.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "triage_cases")
public class TriageCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "assigned_doctor_id")
    private Doctor assignedDoctor;

    private LocalDateTime caseDate = LocalDateTime.now();

    @Column(nullable = false)
    private String chiefComplaint;

    private String urgencyLevel;
    private String recommendation;
    private String status;
    private Boolean redFlagFound;

    public TriageCase() {}

    public Long getCaseId() { return caseId; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getAssignedDoctor() { return assignedDoctor; }
    public void setAssignedDoctor(Doctor assignedDoctor) { this.assignedDoctor = assignedDoctor; }

    public LocalDateTime getCaseDate() { return caseDate; }
    public void setCaseDate(LocalDateTime caseDate) { this.caseDate = caseDate; }

    public String getChiefComplaint() { return chiefComplaint; }
    public void setChiefComplaint(String chiefComplaint) { this.chiefComplaint = chiefComplaint; }

    public String getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(String urgencyLevel) { this.urgencyLevel = urgencyLevel; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getRedFlagFound() { return redFlagFound; }
    public void setRedFlagFound(Boolean redFlagFound) { this.redFlagFound = redFlagFound; }
}