package com.healthcare.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Information submitted for a new triage case")
public class TriageRequest {

    @Schema(
        description = "ID of the patient submitting symptoms",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @Schema(
        description = "Main symptom or medical concern reported by the patient",
        example = "Chest pain and severe difficulty breathing",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Chief complaint is required")
    @Size(
        min = 5,
        max = 2000,
        message = "Chief complaint must be between 5 and 2000 characters"
    )
    private String chiefComplaint;

    public TriageRequest() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }
}