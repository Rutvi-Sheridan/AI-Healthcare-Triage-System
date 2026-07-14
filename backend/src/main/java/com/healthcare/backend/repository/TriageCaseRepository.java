package com.healthcare.backend.repository;

import com.healthcare.backend.model.TriageCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TriageCaseRepository
        extends JpaRepository<TriageCase, Long> {

    List<TriageCase> findByPatientPatientId(Long patientId);

    List<TriageCase> findByUrgencyLevel(String urgencyLevel);

    List<TriageCase> findByStatus(String status);
}