package com.healthcare.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.backend.dto.TriageRequest;
import com.healthcare.backend.dto.TriageResponse;
import com.healthcare.backend.model.Patient;
import com.healthcare.backend.model.TriageCase;
import com.healthcare.backend.model.TriageStatus;
import com.healthcare.backend.model.UrgencyLevel;
import com.healthcare.backend.repository.PatientRepository;
import com.healthcare.backend.repository.TriageCaseRepository;

@Service
public class TriageService {

    private final TriageCaseRepository triageCaseRepository;
    private final PatientRepository patientRepository;

    public TriageService(
            TriageCaseRepository triageCaseRepository,
            PatientRepository patientRepository) {

        this.triageCaseRepository = triageCaseRepository;
        this.patientRepository = patientRepository;
    }

    public TriageResponse createCase(TriageRequest request) {

        Patient patient = patientRepository
                .findById(request.getPatientId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found."
                        )
                );

        String complaint =
                request.getChiefComplaint().trim();

        Classification classification =
                classifyComplaint(complaint);

        TriageCase triageCase = new TriageCase();

        triageCase.setPatient(patient);
        triageCase.setChiefComplaint(complaint);
        triageCase.setUrgencyLevel(
                classification.urgencyLevel()
        );
        triageCase.setRecommendation(
                classification.recommendation()
        );
        triageCase.setRedFlagFound(
                classification.redFlagFound()
        );
        triageCase.setStatus(TriageStatus.PENDING);

        return toResponse(
                triageCaseRepository.save(triageCase)
        );
    }

    public List<TriageResponse> getAllCases() {

        return triageCaseRepository
                .findAllByOrderByCaseDateDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TriageResponse> getPatientCases(
            Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new IllegalArgumentException(
                    "Patient not found."
            );
        }

        return triageCaseRepository
                .findByPatientPatientIdOrderByCaseDateDesc(
                        patientId
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TriageResponse getCaseById(Long caseId) {

        return toResponse(
                getEntity(caseId)
        );
    }

    public TriageResponse updateStatus(
            Long caseId,
            TriageStatus status) {

        TriageCase triageCase = getEntity(caseId);

        triageCase.setStatus(status);

        return toResponse(
                triageCaseRepository.save(triageCase)
        );
    }

    private TriageCase getEntity(Long caseId) {

        return triageCaseRepository
                .findById(caseId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Triage case not found."
                        )
                );
    }

    private Classification classifyComplaint(
            String complaint) {

        String symptoms = complaint.toLowerCase();

        boolean emergency =
                symptoms.contains("chest pain")
                || symptoms.contains("difficulty breathing")
                || symptoms.contains("cannot breathe")
                || symptoms.contains("unconscious")
                || symptoms.contains("severe bleeding")
                || symptoms.contains("stroke")
                || symptoms.contains("suicidal");

        if (emergency) {
            return new Classification(
                    UrgencyLevel.EMERGENCY,
                    true,
                    "Emergency warning detected. Seek immediate emergency assistance."
            );
        }

        boolean high =
                symptoms.contains("high fever")
                || symptoms.contains("severe pain")
                || symptoms.contains("vomiting blood")
                || symptoms.contains("head injury");

        if (high) {
            return new Classification(
                    UrgencyLevel.HIGH,
                    false,
                    "Seek urgent medical assessment as soon as possible."
            );
        }

        boolean medium =
                symptoms.contains("fever")
                || symptoms.contains("infection")
                || symptoms.contains("persistent")
                || symptoms.contains("worsening");

        if (medium) {
            return new Classification(
                    UrgencyLevel.MEDIUM,
                    false,
                    "Contact a family doctor or walk-in clinic."
            );
        }

        return new Classification(
                UrgencyLevel.LOW,
                false,
                "Monitor your symptoms and consider routine medical follow-up."
        );
    }

    private TriageResponse toResponse(
            TriageCase triageCase) {

        TriageResponse response =
                new TriageResponse();

        response.setCaseId(
                triageCase.getCaseId()
        );

        response.setPatientId(
                triageCase.getPatient().getPatientId()
        );

        response.setPatientName(
                triageCase.getPatient()
                        .getUser()
                        .getName()
        );

        response.setCaseDate(
                triageCase.getCaseDate()
        );

        response.setChiefComplaint(
                triageCase.getChiefComplaint()
        );

        response.setUrgencyLevel(
                triageCase.getUrgencyLevel()
        );

        response.setRecommendation(
                triageCase.getRecommendation()
        );

        response.setRedFlagFound(
                triageCase.isRedFlagFound()
        );

        response.setStatus(
                triageCase.getStatus()
        );

        return response;
    }

    private record Classification(
            UrgencyLevel urgencyLevel,
            boolean redFlagFound,
            String recommendation) {
    }
}