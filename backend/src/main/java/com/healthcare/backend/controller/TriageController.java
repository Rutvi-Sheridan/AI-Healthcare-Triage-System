package com.healthcare.backend.controller;

import com.healthcare.backend.model.TriageCase;
import com.healthcare.backend.repository.TriageCaseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/triage")
@CrossOrigin(origins = "http://localhost:3000")
public class TriageController {

    private final TriageCaseRepository triageCaseRepository;

    public TriageController(
            TriageCaseRepository triageCaseRepository) {
        this.triageCaseRepository = triageCaseRepository;
    }

    // Test endpoint
    @GetMapping("/test")
    public String testApi() {
        return "Triage API is working!";
    }

    // GET all triage cases
    @GetMapping
    public ResponseEntity<List<TriageCase>> getAllTriageCases() {
        return ResponseEntity.ok(triageCaseRepository.findAll());
    }

    // GET one triage case
    @GetMapping("/{id}")
    public ResponseEntity<TriageCase> getTriageCaseById(
            @PathVariable Long id) {

        return triageCaseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE triage case
    @PostMapping
    public ResponseEntity<TriageCase> createTriageCase(
            @RequestBody TriageCase triageCase) {

        TriageCase savedCase =
                triageCaseRepository.save(triageCase);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCase);
    }

    // UPDATE triage case
    @PutMapping("/{id}")
    public ResponseEntity<TriageCase> updateTriageCase(
            @PathVariable Long id,
            @RequestBody TriageCase updatedCase) {

        return triageCaseRepository.findById(id)
                .map(existingCase -> {
                    existingCase.setChiefComplaint(
                            updatedCase.getChiefComplaint());

                    existingCase.setRecommendation(
                            updatedCase.getRecommendation());

                    existingCase.setRedFlagFound(
                            updatedCase.isRedFlagFound());

                    existingCase.setStatus(
                            updatedCase.getStatus());

                    existingCase.setUrgencyLevel(
                            updatedCase.getUrgencyLevel());

                    existingCase.setPatient(
                            updatedCase.getPatient());

                    existingCase.setAssignedDoctor(
                            updatedCase.getAssignedDoctor());

                    TriageCase savedCase =
                            triageCaseRepository.save(existingCase);

                    return ResponseEntity.ok(savedCase);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE triage case
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTriageCase(
            @PathVariable Long id) {

        if (!triageCaseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        triageCaseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}