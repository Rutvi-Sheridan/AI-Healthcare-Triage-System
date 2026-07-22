package com.healthcare.backend.service;

import com.healthcare.backend.exception.ResourceNotFoundException;
import com.healthcare.backend.model.TriageCase;
import com.healthcare.backend.repository.TriageCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TriageService {

    private final TriageCaseRepository triageCaseRepository;

    public TriageService(TriageCaseRepository triageCaseRepository) {
        this.triageCaseRepository = triageCaseRepository;
    }

    // Get all triage cases
    public List<TriageCase> getAllTriageCases() {
        return triageCaseRepository.findAll();
    }

    // Get triage case by ID
    public TriageCase getTriageCaseById(Long id) {
        return triageCaseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Triage case not found with id: " + id));
    }

    // Create triage case
    public TriageCase createTriageCase(TriageCase triageCase) {
        return triageCaseRepository.save(triageCase);
    }

    // Update triage case
    public TriageCase updateTriageCase(Long id, TriageCase updatedCase) {

        TriageCase existingCase = getTriageCaseById(id);

        existingCase.setChiefComplaint(updatedCase.getChiefComplaint());
        existingCase.setRecommendation(updatedCase.getRecommendation());
        existingCase.setRedFlagFound(updatedCase.isRedFlagFound());
        existingCase.setStatus(updatedCase.getStatus());
        existingCase.setUrgencyLevel(updatedCase.getUrgencyLevel());
        existingCase.setPatient(updatedCase.getPatient());
        existingCase.setAssignedDoctor(updatedCase.getAssignedDoctor());

        return triageCaseRepository.save(existingCase);
    }

    // Delete triage case
    public void deleteTriageCase(Long id) {

        TriageCase existingCase = getTriageCaseById(id);

        triageCaseRepository.delete(existingCase);
    }
}