package com.healthcare.backend.dto;

import java.time.LocalDateTime;

import com.healthcare.backend.model.TriageStatus;
import com.healthcare.backend.model.UrgencyLevel;

public class TriageResponse {

    private Long caseId;
    private Long patientId;
    private String patientName;
    private LocalDateTime caseDate;
    private String chiefComplaint;
    private UrgencyLevel urgencyLevel;
    private String recommendation;
    private boolean redFlagFound;
    private TriageStatus status;

    public TriageResponse() {
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
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
}
