package com.healthcare.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.backend.model.TriageCase;
import com.healthcare.backend.model.TriageStatus;
import com.healthcare.backend.model.UrgencyLevel;

@Repository
public interface TriageCaseRepository
        extends JpaRepository<TriageCase, Long> {

    List<TriageCase> findByUrgencyLevel(
            UrgencyLevel urgencyLevel
    );

    List<TriageCase> findByStatus(
            TriageStatus status
    );

    List<TriageCase> findAllByOrderByCaseDateDesc();

    List<TriageCase>
            findByPatientPatientIdOrderByCaseDateDesc(
                    Long patientId
            );
}